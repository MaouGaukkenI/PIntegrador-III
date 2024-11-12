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
 * @author MGAukken__
 */
@Controller

@RequestMapping("/Tar")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private HTService htService;

    /**
     * Retorna os ids em uma lista JSON
     *
     * @return ids
     */
    @GetMapping("/listarIds")
    public ResponseEntity<List<Integer>> getAllIds() {
        List<Integer> ids = tarefaService.findAllIds();
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    /**
     * Retorna as tarefas em um lista JSON
     *
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<List> getAllTar() {

        return new ResponseEntity<>(tarefaService.listarTarefas(), HttpStatus.OK);
    }

    /**
     * Cria uma tarefa
     *
     * @param tar entidade de formatação
     * @return um JSON com a tarefa criada
     */
    @PostMapping("/adicionar")
    public ResponseEntity<Tarefa> addAnalise(@RequestBody Tarefa tar) {
        Tarefa novaTarefa = tarefaService.criarTarefa(tar);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    /**
     * Realiza uma pesquisa e retorna uma tarefa de acordo co o id enviado.
     *
     * @param id id para busca da tarefa.
     * @return tarefa encontrada em JSON ou mensagem de erro
     */
    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Integer id) {

        Tarefa tar = tarefaService.getTarefaById(id);

        return new ResponseEntity<>(tar, HttpStatus.OK);
    }

    /**
     * Cadastro atraves do metodo GET.
     *
     * @param tar entidade de formatação.
     * @param usId id do usuario.
     * @param tit titulo da tarefa.
     * @param dat data final da tarefa.
     * @param des descrição da tarefa.
     * @param sta status da tareefa.
     * @return
     */
    @GetMapping("/cadAt")
    public String cadAt(Tarefa tar, @RequestParam(required = false) Integer usId, @RequestParam(required = false) String tit, @RequestParam(required = false) String dat, @RequestParam(required = false) String des, @RequestParam(required = false) String sta) {
        tarefaService.cadAt(tar, usId, tit, dat, des, sta);

        return "index";
    }

    /**
     * Exclui a tarefa cujo possua o id enviado
     *
     * @param id identifiqca a tarefa desejada.
     * @return
     */
    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellAt(@PathVariable Integer id) {
        boolean foiRemovido = tarefaService.deletarTarefa(id);

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
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Integer id, @RequestBody Tarefa novaTarefa) {
        Tarefa tarefaEditada = tarefaService.editarTar(id, novaTarefa);
        if (tarefaEditada != null) {
            return new ResponseEntity<>(tarefaEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Envia uma tarefa para o historico de acordo com seu id.
     *
     * @param id identifica a tarefa.
     * @return valor booleano para execução do metodo.
     */
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
