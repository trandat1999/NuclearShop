package com.tranhuudat.nuclearshop.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtProvider {

    private final String secretKey = "thisistokensecretqwertyuiopasdfghjklzxcvbnm";

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withSubject(principal.getUsername())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(900)))
                .sign(algorithm);
        //        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(Date.from(Instant.now()))
//        .signWith(Algorithm.HMAC256(secretKey))
//        .setExpiration(Date.from(Instant.now().plusSeconds(900))).compact();
    }

    public String generateTokenWithUsername(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withSubject(username)
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(900)))
                .sign(algorithm);
//        return Jwts.builder().setSubject(username).setIssuedAt(Date.from(Instant.now()))
//        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
//        .setExpiration(Date.from(Instant.now().plusSeconds(900))).compact();
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }

//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (SignatureException ex) {
//            return false;
//        } catch (MalformedJwtException ex) {
//            return false;
//        } catch (ExpiredJwtException ex) {
//            return false;
//        } catch (UnsupportedJwtException ex) {
//            return false;
//        } catch (IllegalArgumentException ex) {
//            return false;
//        }
    }

    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
