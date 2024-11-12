/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.controller;

import com.senac.atividades.data.Historico;
import com.senac.atividades.service.HTService;
import com.senac.atividades.service.HistoricoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MGaukken__
 */
@Controller

@RequestMapping("/His")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private HTService htService;

    /**
     * Retorna os ids em uma lista JSON
     *
     * @return ids
     */
    @GetMapping("/listarIds")
    public ResponseEntity<List<Integer>> getAllIds() {
        List<Integer> ids = historicoService.findAllIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    /**
     * Retorna as tarefas em um lista JSON
     *
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<List> getAllTar() {

        return new ResponseEntity<>(historicoService.listarHistorico(), HttpStatus.OK);
    }

    /**
     * Cria uma tarefa
     *
     * @param his entidade de formatação
     * @return um JSON com a tarefa criada
     */
    @PostMapping("/adicionar")
    public ResponseEntity<Historico> addAnalise(@RequestBody Historico his) {
        Historico novaTarefa = historicoService.criarTarefa(his);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    /**
     * Realiza uma pesquisa e retorna uma tarefa de acordo co o id enviado.
     *
     * @param id id para busca da tarefa.
     * @return tarefa encontrada em JSON ou mensagem de erro
     */
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Historico> getTarefaById(@PathVariable Integer id) {

        Historico his = historicoService.getTarefaById(id);

        return new ResponseEntity<>(his, HttpStatus.OK);
    }

    /**
     * Exclui a tarefa cujo possua o id enviado
     *
     * @param id identifiqca a tarefa desejada.
     * @return
     */
    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellAt(@PathVariable Integer id) {
        boolean foiRemovido = historicoService.deletarTarefa(id);

        if (foiRemovido) {
            return new ResponseEntity<>("Tarefa deletada com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Edita uma tarefa de acordo com o id enviado
     *
     * @param id indetifica a tarefa
     * @param novaTarefa dados para edição.
     * @return Tarefa editada em JSON.
     */
    @PutMapping("/editar/{id}")
    public ResponseEntity<Historico> editarTarefa(@PathVariable Integer id, @RequestBody Historico novaTarefa) {
        Historico tarefaEditada = historicoService.editarTar(id, novaTarefa);
        if (tarefaEditada != null) {
            return new ResponseEntity<>(tarefaEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Envia uma tarefa para a aba de tarefas de acordo com seu id.
     *
     * @param id identifica a tarefa.
     * @return valor booleano para execução do metodo.
     */
    @PostMapping("/recTar/{id}")
    public ResponseEntity<String> recuperarTarefa(@PathVariable Integer id) {
        boolean sucesso = htService.moverParaTarefas(id);

        if (sucesso) {
            return ResponseEntity.ok("Tarefa recuperada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao recuperar tarefa.");
        }
    }

    /**
     * Cadastro atraves do metodo GET.
     *
     * @param his entidade de formatação.
     * @param usId id do usuario.
     * @param tit titulo da tarefa.
     * @param dat data final da tarefa.
     * @param des descrição da tarefa.
     * @param sta status da tareefa.
     * @return
     */
    @GetMapping("/cadAt")
    public String cadAt(Historico his, @RequestParam(required = false) Integer usId, @RequestParam(required = false) String tit, @RequestParam(required = false) String dat, @RequestParam(required = false) String des, @RequestParam(required = false) String sta) {
        historicoService.cadAt(his, usId, tit, dat, des, sta);

        return "index";
    }
}
