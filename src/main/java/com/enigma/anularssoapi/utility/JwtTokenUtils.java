package com.enigma.anularssoapi.utility;

import com.enigma.anularssoapi.service.anularuser.details.AnularUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    @Autowired
    AnularUserDetailsService anularUserDetailsService;

    @Value("ThisIsForEncryptedPasswordTHatHashingSome FunctionThrougThe PAsswordAnd The Pasword is 23rhob3oirb2i3hrifhuw4hr2u3rhefb2i3hrugbi3rhoq2")
    private String secret;

    public String generateToken(UserDetails userDetails, Integer minute){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Timestamp(System.currentTimeMillis()))
                .setExpiration(new Timestamp(System.currentTimeMillis()+(1000L*60*minute)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public UserDetails parseToken(String token){
        Jws<Claims> jws = getClaimsJws(token);

        String username = jws.getBody().getSubject();
        return anularUserDetailsService.loadUserByUsername(username);
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);
    }
}
