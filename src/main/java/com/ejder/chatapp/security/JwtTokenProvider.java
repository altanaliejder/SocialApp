package com.ejder.chatapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${chat.app.secret}")
    private String APP_SECRET;
    @Value("${chat.app.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails=(JwtUserDetails) auth.getPrincipal();
        Date expireDate=new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }

    String getUsernameFromJwt(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }

    }

    private boolean isTokenExpired(String token) {
        Date expiration=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
