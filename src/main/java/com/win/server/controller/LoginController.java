package com.win.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.win.server.model.*;
import com.win.server.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    AuthService authService;

    @PostMapping("/basic")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TokenResponse basicLogin(@RequestBody LoginRequestModel data) {
             return authService.usernamePasswordLogin(data.getUsername(), data.getPassword());
    }

    @GetMapping("/oauth2/google/authorization")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getAuthorizationURLGoogle() {
        return "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fgoogle&prompt=consent&response_type=code&client_id=286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&access_type=offline";
    }

    @GetMapping("/oauth2/google")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OAuthUserInfo getCodeGoogle(@RequestParam String code, HttpServletResponse response) throws IOException {
        System.out.println(code);
        String getTokenURL = "https://oauth2.googleapis.com/token";
        String client_id = "286076435907-6nfi8v3sd5gchj8rp091gncul553um6f.apps.googleusercontent.com";
        String client_secret = "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";
        String grant_type = "authorization_code";
        String redirect_uri = "http://localhost:8080/login/oauth2/google";
        String access_type = "offline";
        OAuthTokenRequest requestData = new OAuthTokenRequest(client_id,client_secret,code,grant_type,redirect_uri,access_type);
        //------------INITIALIZE GET TOKEN REQUESR-----------------
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
        return userInfo;
    }
}