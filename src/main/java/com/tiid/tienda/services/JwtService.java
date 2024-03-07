package com.tiid.tienda.services;

import com.tiid.tienda.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private final ProfileService profileService;

    @Autowired
    public JwtService(ProfileService profileService){
        this.profileService = profileService;
    }

    @Value("${jwt.secret-key}")
    private String jwtSecret;


    public String generateToken( User user){
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = profileService.getPermissions(user);

        claims.put("name", user.getName());
        claims.put("role", user.getRole());
        claims.put("authorities", authorities);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] llaveBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(llaveBytes);
    }

    public String getUserFromToken(String token) {
        Claims claims = getAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, User user) {
//        final String email = getUserFromToken(token);
//        return (email.equals(user.getEmail()) && !isTokenExpired(token));
        return true;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> jwts = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
                System.out.println(e);
            }
            return false;
        }

    public Claims getAllClaims(String token){


        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String cleanToken(String token){
        return token.substring(7);

    }


    public <T> T getClaims(String token, Function<Claims, T> claimsReolver){
        final Claims claims=getAllClaims(token);
        return claimsReolver.apply(claims);
    }


}
