/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.controller;

import com.senac.atividades.coockie.JwtUtil;
import com.senac.atividades.data.TarefaEntity;
import com.senac.atividades.data.UsuarioEntity;
import com.senac.atividades.service.HTService;
import com.senac.atividades.service.HistoricoService;
import com.senac.atividades.service.TarefaService;
import com.senac.atividades.service.UsuarioService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author TheDe
 */
@Controller
public class SiteController {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private HTService htService;

    @GetMapping("/")
    public String Menu() {
        return "index";
    }

    @GetMapping("/Historico")
    public String Historico(Model model, @CookieValue("jwtTokenId") String token) throws Exception {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String userId = claims.getSubject();
            Integer id = Integer.valueOf(userId);
            model.addAttribute("listarTarefas", historicoService.listarHistoricoByUId(id));
            return "historico";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/Tarefas")
    public String Tarefas(Model model, @CookieValue("jwtTokenId") String token) throws Exception {
        try {
            Claims claims = JwtUtil.validateToken(token);
            String userId = claims.getSubject();
            Integer id = Integer.valueOf(userId);
            model.addAttribute("listarTarefas", tarefaService.listarTarefasByUId(id));
            return "tarefas";
        } catch (NumberFormatException e) {
            return "redirect:/";
        }
    }

    @PostMapping("/editar/{id}")
    public String EditarTarefa(@PathVariable Integer id, @RequestBody TarefaEntity novaTarefa, Model model) {
        tarefaService.editarTar(id, novaTarefa);
        model.addAttribute("listarTarefas", tarefaService.listarTarefas());
        return "redirect:/Tarefas";
    }

    @PostMapping("/AddTar")
    public String AddTar(TarefaEntity novaTarefa, Model model) {
        tarefaService.criarTarefa(novaTarefa);
        model.addAttribute("listarTarefas", tarefaService.listarTarefas());
        return "redirect:/Tarefas";
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

    @PostMapping("/recTar/{id}")
    public ResponseEntity<String> recuperarTarefa(@PathVariable Integer id) {
        boolean sucesso = htService.moverParaTarefas(id);

        if (sucesso) {
            return ResponseEntity.ok("Tarefa recuperada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao recuperar tarefa.");
        }
    }

    @DeleteMapping("/dell/{id}")
    public ResponseEntity<String> dellAt(@PathVariable Integer id) {
        boolean foiRemovido = historicoService.deletarTarefa(id);

        if (foiRemovido) {
            return new ResponseEntity<>("Tarefa deletada com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
