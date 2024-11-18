package com.senac.atividades.coockie;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TheDe
 */
public class JwtUtil {

    private static final String SECRET_KEY = "httpservercheckuser";

    private static final Set<String> invalidatedTokens = new HashSet<>();

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) throws Exception {
        if (isTokenInvalidated(token)) {
            throw new Exception("Token has been invalidated");
        }
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public static boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
