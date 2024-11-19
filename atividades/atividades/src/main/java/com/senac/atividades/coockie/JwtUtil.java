package com.senac.atividades.coockie;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Classe responsável pela manipulação de tokens JWT. Contém métodos para gerar,
 * validar e invalidar tokens.
 */
public class JwtUtil {

    private static final String SECRET_KEY = "httpservercheckuser";

    // Lista de tokens invalidados
    private static final List<String> invalidatedTokens = new ArrayList<>();

    /**
     * Gera um token JWT para um usuário.
     *
     * @param userId ID do usuário para gerar o token.
     * @return O token JWT gerado.
     */
    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Valida o token JWT. Verifica se o token foi invalidado e se é válido.
     *
     * @param token O token JWT a ser validado.
     * @return Os claims do token, se válido.
     * @throws Exception Se o token for inválido ou estiver na lista de tokens
     * invalidados.
     */
    public static Claims validateToken(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new Exception("Invalid token", e);  // Em caso de erro ao decodificar o token
        }
    }

    public static boolean userLoggedIn(String token) throws Exception {
        Claims claims = JwtUtil.validateToken(token);
        String user = claims.getSubject();
        
        return !isTokenInvalidated(user);
    }

    /**
     * Invalidar um token JWT.
     *
     * @param token O token JWT a ser invalidado.
     */
    public static void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
    
    /**
     * Ativar um token JWT.
     *
     * @param token O token JWT a ser ativado.
     */
    public static void ativateToken(String token) {
        invalidatedTokens.remove(token);
    }

    /**
     * Verifica se um token foi invalidado.
     *
     * @param token O token JWT a ser verificado.
     * @return Verdadeiro se o token foi invalidado, falso caso contrário.
     */
    public static boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
