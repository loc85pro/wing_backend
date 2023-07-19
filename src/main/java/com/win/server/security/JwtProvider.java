package com.win.server.security;

import java.util.Date;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Component
public class JwtProvider {
    private final String JWT_SECRET = "PhanLocccc";
    public String generateToken(String username, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return (String)Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();

    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
