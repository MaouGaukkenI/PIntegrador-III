package com.senac.atividades.controller;

import com.senac.atividades.coockie.JwtUtil;
import com.senac.atividades.data.UsuarioEntity;
import com.senac.atividades.service.UsuarioService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TheDe
 */
@RestController
public class ProtectedController {

    @Autowired
    private UsuarioService userService;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData, HttpServletResponse response) {
        String login = loginData.get("login");
        String senha = loginData.get("senha");
        System.out.println(loginData);
        return (ResponseEntity<String>) userService.login(login, senha, response);
    }

    @GetMapping("/getUserId")
    public ResponseEntity<String> getUserId(@CookieValue("jwtTokenId") String token) {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String userId = claims.getSubject();
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<String> getUser(@CookieValue("jwtTokenUser") String token) {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String user = claims.getSubject();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }

    /**
     * Edita uma tarefa de acordo com o id enviado
     *
     * @param token indetifica a tarefa.
     * @param novoUsuario dados para edição.
     * @return Tarefa editada em JSON.
     */
    @PutMapping("/editarUser")
    public ResponseEntity<UsuarioEntity> editarTarefa(@CookieValue("jwtTokenUser") String token, @RequestBody UsuarioEntity novoUsuario) {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String user = claims.getSubject();
            UsuarioEntity userEditado = userService.editarUser(user, novoUsuario);
            if (userEditado != null) {
                return new ResponseEntity<>(userEditado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Exclui o usuario e suas dependencias cujo possua o id enviado
     *
     * @param token identifiqca o usuario desejado.
     * @return
     * @throws java.lang.Exception
     */
    @DeleteMapping("/dellUser")
    public ResponseEntity<String> dellUser(@CookieValue("jwtTokenId") String token) throws Exception {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String userId = claims.getSubject();
            boolean foiRemovido = userService.dellUsuario(Integer.valueOf(userId));

            if (foiRemovido) {
                return new ResponseEntity<>("Usuario deletada com sucesso", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario não encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }
}
