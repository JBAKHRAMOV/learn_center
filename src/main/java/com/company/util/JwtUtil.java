package com.company.util;

import com.company.dto.JwtDTO;
import com.company.exceptions.TimeExpiredException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private final static String SECRET_KEY = "aniq topolmaysan";

    public static String createJwt(Long id, String username) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.claim("userName", username);
        jwtBuilder.setIssuer("DAVR_BANK");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeJwt(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser();

            jwtParser.setSigningKey(SECRET_KEY);
            Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = jws.getBody();
            Integer id = Integer.valueOf(claims.getSubject());
            String userName = (String) claims.get("userName");
            return new JwtDTO(id, userName);
        } catch (JwtException e) {
            throw new TimeExpiredException("JWT time expired");
        }

    }

}
