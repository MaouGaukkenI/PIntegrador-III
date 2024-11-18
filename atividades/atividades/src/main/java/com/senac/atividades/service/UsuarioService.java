/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.coockie.JwtUtil;
import com.senac.atividades.data.UsuarioEntity;
import com.senac.atividades.data.UsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author MGaukken__
 */
@Service
public class UsuarioService {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<?> login(String login, String senha, HttpServletResponse response) {
        UsuarioEntity usuario = findByLogin(login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            String tokenId = JwtUtil.generateToken(String.valueOf(usuario.getId()));
            String tokenUser = JwtUtil.generateToken(String.valueOf(usuario.getLogin()));

            Cookie jwtCookieI = new Cookie("jwtTokenId", tokenId);
            jwtCookieI.setHttpOnly(true);
            jwtCookieI.setPath("/");
            jwtCookieI.setMaxAge(86400);
            response.addCookie(jwtCookieI);

            Cookie jwtCookieU = new Cookie("jwtTokenUser", tokenUser);
            jwtCookieU.setHttpOnly(true);
            jwtCookieU.setPath("/");
            jwtCookieU.setMaxAge(86400);
            response.addCookie(jwtCookieU);

            // Enviar o token no corpo da resposta como JSON
            return ResponseEntity.ok(Map.of("token", tokenId));  // Modificado aqui
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }
    }

    public UsuarioEntity findByLogin(String login) {
        UsuarioEntity usuario = usuarioRepository.findUsuarioByLogin(login);
        return usuario;
    }

    /**
     * mecanismo para teste que retorna o nome dos usuarios.
     *
     * @return todos os nomes de usuario.
     */
    public List<String> showUserNames() {
        List<String> users = usuarioRepository.findAllLogins();
        return users;
    }

    /**
     * Retorna uma lista com todos os ids de usuarios cadastrados.
     *
     * @return Lista retornada.
     */
    public List<Integer> findAllIds() {
        List<Integer> ids = usuarioRepository.findAllIds();
        return ids;
    }

    /**
     * Mecanismo para buscar um id vago em meio a uma lista com os ids
     * cadastrados.
     *
     * @param ids Lista com os ids.
     * @return um id livre.
     */
    public Integer findMissingId(List<Integer> ids) {
        int n = ids.size();
        for (int i = 1; i <= n + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Permite criar um usuario.
     *
     * @param user entidade de formatação.
     * @return o usuario criado em JSON.
     */
    public UsuarioEntity criarUsuario(UsuarioEntity user) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            user.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            user.setId(nextId);
        }

        usuarioRepository.save(user);
        return user;
    }

    public boolean deletarUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true; // Tarefa deletada com sucesso
        }
        return false; // Tarefa não encontrada
    }

    /**
     * Deleta o usuario e todas tarefas relacionadas a ele.
     *
     * @param id id do usuario.
     * @return boleano com resultado da exclusão do usuario.
     */
    @Transactional
    public boolean dellUsuario(Integer id) {
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;

        try {
            result1 = deletarUsuario(id); // Deleta o usuário
            result2 = historicoService.dellByUserID(id); // Deleta o histórico do usuário
            result3 = tarefaService.dellByUserID(id); // Deleta as tarefas do usuário
        } catch (Exception e) {
            System.out.println("Erro ao deletar os dados do usuário: " + e.getMessage());
        }

        return result1 && result2 && result3; // Retorna verdadeiro se todas as exclusões forem bem-sucedidas
    }

    /**
     * Permite que uma tarefa da aba de tarefas seja editada de acordo com o id
     * enviado.
     *
     * @param login utilizado para determinar qual usuario foi selecionado.
     * @param u entidade de formatação.
     * @return a tarefa editada em JSON.
     */
    public UsuarioEntity editarUser(String login, UsuarioEntity u) {
        UsuarioEntity user = findByLogin(login);
        return usuarioRepository.findById(user.getId()).map(usuarioExistente -> {
            usuarioExistente.setLogin(u.getLogin());
            usuarioExistente.setSenha(u.getSenha());
            
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuario não encontrada com o id: " + user.getId()));
    }
}
