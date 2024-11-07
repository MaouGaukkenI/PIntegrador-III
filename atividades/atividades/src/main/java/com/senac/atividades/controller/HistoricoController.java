/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.controller;

import com.senac.atividades.data.Historico;
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
 * @author TheDe
 */
@Controller

@RequestMapping("/His")
public class HistoricoController {
    @Autowired
    private HistoricoService historicoService;
    
    @GetMapping("/listarIds")
    public ResponseEntity<List<Integer>> getAllIds(){
        List<Integer> ids = historicoService.findAllIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
    
    
    @GetMapping("/listar") 
    public ResponseEntity<List> getAllTar() { 
        
        List<Historico> his = HistoricoService.getHistoricos();

        return new ResponseEntity<>(his, HttpStatus.OK); 
    }
    
    @PostMapping("/adicionar")
    public ResponseEntity<Historico> addAnalise(@RequestBody Historico his) {
        Historico novaTarefa = historicoService.criarTarefa(his);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }
    
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Historico> getTarefaById(@PathVariable Integer id) { 

        Historico his = historicoService.getTarefaById(id); 

        return new ResponseEntity<>(his, HttpStatus.OK); 
    }
    
    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellAt(@PathVariable Integer id){
        boolean foiRemovido = historicoService.deletarTarefa(id);

        if (foiRemovido) {
            return new ResponseEntity<>("Tarefa deletada com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<Historico> editarTarefa(@PathVariable Integer id, @RequestBody Historico novaTarefa) {
        Historico tarefaEditada = historicoService.editarTar(id, novaTarefa);
        if (tarefaEditada != null) {
            return new ResponseEntity<>(tarefaEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/")
    public String Menu(){
        return "index";
    }
    
    @GetMapping("/cadAt")
    public String cadAt(Historico ati, @RequestParam(required = false) String tit, @RequestParam(required = false) String dat, @RequestParam(required = false) String des, @RequestParam(required = false) String sta){
        historicoService.cadAt(ati, tit, dat, des, sta);
        
        return"index";
    }
}
