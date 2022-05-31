package com.example.springboot;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import java.util.ServiceLoader;
import io.jsonwebtoken.io.Serializer;
public class JsonToken {


    public static String generateToken(String email,String password){
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        return Jwts.builder()
                .claim("email", email)
                .claim("password", password)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7, ChronoUnit.DAYS))).signWith(SignatureAlgorithm.HS256,hmacKey).compact();
    }

    public static String generateTokenForOTP(String email,String password){
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        return Jwts.builder()
                .claim("email", email)
                .claim("password", password)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(60, ChronoUnit.SECONDS))).signWith(SignatureAlgorithm.HS256,hmacKey).compact();
    }
    public static String parseJwt(String jwtString) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parser()
                .setSigningKey(hmacKey)
                .parseClaimsJws(jwtString).toString();
    }

    public static boolean verifyToken(String token){
        try{
            String userData = parseJwt(token);
            if(!Objects.equals(userData, "")){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

}
