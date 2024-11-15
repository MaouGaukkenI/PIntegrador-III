/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.controller;

import com.senac.atividades.coockie.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TheDe
 */
@RestController
public class ProtectedController {
    @GetMapping("/protected")
    public String protectedEndpoint(@RequestHeader("Authorization") String token) {
        try {
            Claims claims = JwtUtil.validateToken(token.replace("Bearer ", ""));
            String userId = claims.getSubject();
            return "Acesso concedido ao usuário com ID: " + userId;
        } catch (Exception e) {
            return "Token inválido ou expirado";
        }
    }
}
