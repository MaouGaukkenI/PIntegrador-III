/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.controller;

import com.senac.atividades.data.Usuario;
import com.senac.atividades.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TheDe
 */
@Controller

@RequestMapping("/User")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    /**
     * Mostra uma lista com o nome dos usuarios em JSON.
     *
     * @return os nomes de usuario.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<String>> getAllUserNames() {
        List<String> UN = userService.showUserNames();
        return new ResponseEntity<>(UN, HttpStatus.OK);
    }

    /**
     * Metodo para adicionar um usuario ao sistema.
     *
     * @param user entidade de formatação.
     * @return o usuario criado e JSON.
     */
    @PostMapping("/cadUser")
    public ResponseEntity<Usuario> addAnalise(@RequestBody Usuario user) {
        Usuario novaTarefa = userService.criarUsuario(user);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    /**
     * Exclui o usuario e suas dependencias cujo possua o id enviado
     *
     * @param id identifiqca a tarefa desejada.
     * @return
     */
    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellUser(@PathVariable Integer id) {
        boolean foiRemovido = userService.dellUsuario(id);

        if (foiRemovido) {
            return new ResponseEntity<>("Usuario deletada com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario não encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
