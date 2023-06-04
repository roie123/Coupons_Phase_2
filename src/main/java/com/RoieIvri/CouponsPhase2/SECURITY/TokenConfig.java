package com.RoieIvri.CouponsPhase2.SECURITY;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenConfig {

    public static final String SECRET_KEY = "1234";

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("userName").toString();
    }
    public boolean isExpirationToken(String token) {
        return new Date().before(this.getExpirationFromToken(token));
    }


    public Date getExpirationFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getExpiration();
    }


    public Map<String, Object> buildClaims(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", user.getUsername());
        claims.put("role", user.getAuthorities());
        return claims;
    }


}
