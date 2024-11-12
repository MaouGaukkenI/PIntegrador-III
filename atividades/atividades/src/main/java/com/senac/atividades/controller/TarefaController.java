package com.senac.atividades.controller;

import org.springframework.stereotype.Controller;
import com.senac.atividades.service.TarefaService;
import com.senac.atividades.data.Tarefa;
import com.senac.atividades.service.HTService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @author TheDe
 */
@Controller

@RequestMapping("/Tar")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private HTService htService;

    @GetMapping("/listarIds")
    public ResponseEntity<List<Integer>> getAllIds() {
        List<Integer> ids = tarefaService.findAllIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> getAllTar() {

        return new ResponseEntity<>(tarefaService.listarTarefas(), HttpStatus.OK);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Tarefa> addAnalise(@RequestBody Tarefa tar) {
        Tarefa novaTarefa = tarefaService.criarTarefa(tar);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Integer id) {

        Tarefa tar = tarefaService.getTarefaById(id);

        return new ResponseEntity<>(tar, HttpStatus.OK);
    }

    @GetMapping("/cadAt")
    public String cadAt(Tarefa ati, @RequestParam(required = false) Integer usId, @RequestParam(required = false) String tit, @RequestParam(required = false) String dat, @RequestParam(required = false) String des, @RequestParam(required = false) String sta) {
        tarefaService.cadAt(ati, usId, tit, dat, des, sta);

        return "index";
    }

    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellAt(@PathVariable Integer id) {
        boolean foiRemovido = tarefaService.deletarTarefa(id);

        if (foiRemovido) {
            return new ResponseEntity<>("Tarefa deletada com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Integer id, @RequestBody Tarefa novaTarefa) {
        Tarefa tarefaEditada = tarefaService.editarTar(id, novaTarefa);
        if (tarefaEditada != null) {
            return new ResponseEntity<>(tarefaEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/endTar/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        boolean sucesso = htService.moverParaHistorico(id);

        if (sucesso) {
            return ResponseEntity.ok("Tarefa finalizada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao finalizar tarefa.");
        }
    }
}
