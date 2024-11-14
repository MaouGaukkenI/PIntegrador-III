/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.data.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static List<Usuario> users = new ArrayList<>();

    /**
     * Lista de usuarios para teste.
     */
    public UsuarioService() {
        users.add(new Usuario(1, "Master", "Login1234"));
    }

    /**
     * mecanismo para teste que retorna o nome dos usuarios.
     *
     * @return todos os nomes de usuario.
     */
    public List<String> showUserNames() {
        List<String> Un = new ArrayList<>();
        for (Usuario tarefa : getUsers()) {
            Un.add(tarefa.getLogin());
        }
        return Un;
    }

    /**
     * Retorna uma lista com todos os ids de usuarios cadastrados.
     *
     * @return Lista retornada.
     */
    public List<Integer> findAllIds() {
        List<Integer> ids = new ArrayList<>();
        for (Usuario tarefa : getUsers()) {
            ids.add(tarefa.getId());
        }
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
    public Usuario criarUsuario(Usuario user) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            user.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            user.setId(nextId);
        }

        getUsers().add(user);
        return user;
    }

    /**
     * Deleta o usuario e todas tarefas relacionadas a ele.
     *
     * @param id id do usuario.
     * @return boleano com resultado da exclusão do usuario.
     */
    public boolean dellUsuario(Integer id) {
        boolean result1 = users.removeIf(usuario -> usuario.getId().equals(id));
        boolean result2 = historicoService.dellByUserID(id);
        boolean result3 = tarefaService.dellByUserID(id);
        return result1 && result2 && result3;
    }

    /**
     * @return os usuaruios
     */
    public static List<Usuario> getUsers() {
        return users;
    }
}
