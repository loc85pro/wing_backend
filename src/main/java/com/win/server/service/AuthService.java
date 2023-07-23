package com.win.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.win.server.entity.GoogleEntity;
import com.win.server.entity.UserEntity;
import com.win.server.exception.myexception.IncorrectPasswordException;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.DTO.OAuthTokenRequest;
import com.win.server.DTO.OAuthTokenResponse;
import com.win.server.DTO.OAuthUserInfo;
import com.win.server.DTO.TokenResponse;
import com.win.server.repository.GoogleRepository;
import com.win.server.repository.UserRepository;
import com.win.server.security.AdminAuthority;
import com.win.server.security.CustomUserDetailService;
import com.win.server.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;
    JwtProvider jwtProvider;
    CustomUserDetailService userDetailService;
    GoogleRepository googleRepository;


    public TokenResponse usernamePasswordLogin(String username, String password) {
        UserEntity user = userRepository.findByUser_name(username);
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getId());
        if (userDetails == null)
            throw new UserNotFoundException(username);
        if (!userDetails.getPassword().equals(password))
            throw new IncorrectPasswordException();
        setUserContext(userDetails);
        String accessToken = jwtProvider.generateToken(username, 120000L); //2 minutes
        String refreshToken = jwtProvider.generateToken(username, 1800000L); //2 minutes
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse emailPasswordLogin(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getId());
        if (userDetails == null)
            throw new UserNotFoundException("email: "+email);
        if (!userDetails.getPassword().equals(password))
            throw new IncorrectPasswordException();
        setUserContext(userDetails);
        String accessToken = jwtProvider.generateToken(userDetails.getUsername(), 120000L); //2 minutes
        String refreshToken = jwtProvider.generateToken(userDetails.getUsername(), 1800000L); //30 minutes
        return new TokenResponse(accessToken, refreshToken);
    }


    public TokenResponse registerUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        setUserContext(userDetailService.loadUserByUsername(userEntity.getId()));
        return generateTokenResponse(userEntity.getId());
    }



    //----------------------------------------------------

    public void setUserContext(UserDetails userDetails) {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new AdminAuthority());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), roles);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    //-------------OAUTH2 GOOGLE--------------

    public TokenResponse oauth2GoogleLogin(String code) throws IOException {
        String getTokenURL = "https://oauth2.googleapis.com/token";
        String client_id = "286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com";
        String client_secret = "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";
        String grant_type = "authorization_code";
        String redirect_uri = "http://localhost:8080/login/oauth2/google";
        String access_type = "offline";
        OAuthTokenRequest requestData = new OAuthTokenRequest(client_id,client_secret,code,grant_type,redirect_uri,access_type);
        //------------INITIALIZE GET TOKEN REQUEST-----------------
        URL url = new URL(getTokenURL);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setDoInput(true);
        //-------------------SET BODY FOR REQUEST----------------------------------------
       Map<String,String> arguments = new HashMap<>();
        arguments.put("client_id", client_id);
        arguments.put("client_secret", client_secret);
        arguments.put("code", code);
        arguments.put("grant_type", grant_type);
        arguments.put("redirect_uri", redirect_uri);
        arguments.put("access_type", access_type);

        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();

        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        //----------------RECEIVE RESPONSE-----------
        InputStream in = http.getInputStream();
        OAuthTokenResponse result = new OAuthTokenResponse();
        String data = "";
        int a;
        while ((a=in.read())>0) {
            data+=(char)a;
        }
        ObjectMapper mapper = new ObjectMapper();
        result = mapper.readValue(data,OAuthTokenResponse.class);
        //-----------------------GET DATA BY ACCESS TOKEN--------------------------------------
        URL dataURL = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token="+result.getAccess_token());
        URLConnection dataCon = dataURL.openConnection();
        HttpURLConnection httpData = (HttpURLConnection)dataCon;
        httpData.setRequestMethod("GET");
        httpData.connect();
        InputStream dataIn = httpData.getInputStream();
        String userDataString = "";
        while ((a=dataIn.read())>0)
            userDataString+=(char)a;
        OAuthUserInfo userInfo = new OAuthUserInfo();
        ObjectMapper mapperData = new ObjectMapper();
        userInfo = mapperData.readValue(userDataString,OAuthUserInfo.class);
        //--------------------- CASE: Account is exist-----
        GoogleEntity myGoogleData = googleRepository.getReferenceById(userInfo.getId());
        if (myGoogleData!=null)
        {
            System.out.println(myGoogleData.getId());
            UserEntity user = userRepository.getReferenceById(myGoogleData.getUser_id());
            setUserContext(userDetailService.loadUserByUsername(user.getUsername()));
            return generateTokenResponse(user.getId());
        }
        //---------------CASE: New user (Register by Google)
        //---- convert response data to GoogleEntity
        //----- SAVE GOOGLE OAuth
        GoogleEntity googleEntity = new GoogleEntity();
        googleEntity.setId(userInfo.getId());
        googleEntity.setName(userInfo.getName());
        googleEntity.setGiven_name(userInfo.getGiven_name());
        googleEntity.setFamily_name(userInfo.getFamily_name());
        googleEntity.setPicture(userInfo.getPicture());
        googleEntity.setLocale(userInfo.getLocale());
        googleEntity.setEmail(userInfo.getEmail());
        googleEntity.setVerified_email(userInfo.isVerified_email());

        googleEntity.setAccess_token(result.getAccess_token());
        googleEntity.setRefresh_token((result.getRefresh_token()));
        googleEntity.setExpires_in(result.getExpires_in());
        googleEntity.setScope(result.getScope());
        googleEntity.setId_token(result.getId_token());

        //---------- USER DATA-----------
        UserEntity newUser = new UserEntity();
        UUID newId = UUID.randomUUID();
        newUser.setId(newId.toString());
        newUser.setFull_name(userInfo.getFamily_name()+" "+userInfo.getGiven_name()); // full = family + given ?
        newUser.setEmail(userInfo.getEmail());
        newUser.setUser_name(userInfo.getEmail());
        newUser.setPassword(newId.toString());
        //---------------
        googleEntity.setUser_id(newUser.getId());
        googleRepository.save(googleEntity);
        return registerUser(newUser);
        //-------------------------
    }

    TokenResponse generateTokenResponse (String id) {
        String accessToken = jwtProvider.generateToken(id,300000L);
        String refreshToken = jwtProvider.generateToken(id, 86400000L);
        return new TokenResponse(accessToken,refreshToken);
    }
}
