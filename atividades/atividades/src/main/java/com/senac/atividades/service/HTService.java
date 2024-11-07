/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.data.Historico;
import com.senac.atividades.data.Tarefa;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TheDe
 */
@Service
public class HTService {
    @Autowired
    private HistoricoService historicoService;
    
    @Autowired
    private TarefaService tarefaService;
    
    public boolean moverParaHistorico(Integer id) {
        Tarefa tarefa = tarefaService.getTarefaById(id);
        if (tarefa != null) {
            Historico historico = new Historico(
                historicoService.findMissingId(historicoService.findAllIds()),
                tarefa.getTitulo(),
                tarefa.getDatat(),
                tarefa.getDescricao(),
                "Finalizada"
            );

            historicoService.criarTarefa(historico);

            return tarefaService.deletarTarefa(id);
        }
        return false;
    }
    
    public boolean moverParaTarefas(Integer id){
        Historico historico = historicoService.getTarefaById(id);
        if (historico != null){
            Tarefa tarefa = new Tarefa(
                tarefaService.findMissingId(tarefaService.findAllIds()),
                historico.getTitulo(),
                historico.getDatat(),
                historico.getDescricao(),
                "Ativo"
            );
            
            tarefaService.criarTarefa(tarefa);
            
            return historicoService.deletarTarefa(id);
        }
        return false;
    }
    
    
}
