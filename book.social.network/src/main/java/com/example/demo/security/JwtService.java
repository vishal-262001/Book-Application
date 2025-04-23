package com.example.demo.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
   @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public  String  generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims =extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims,userDetails,jwtExpiration);
    }

    private String buildToken(Map<String, Object> claims
            , UserDetails userDetails
            , long jwtExipration) {
        var authorities =userDetails .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExipration))
                .claim("authorities",authorities)
                .signWith(getSignKey())
                .compact();
    }

    public  boolean isTokenValid(String token,UserDetails userDetails){
        final String UserName = extractUserName(token);
        return (UserName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
