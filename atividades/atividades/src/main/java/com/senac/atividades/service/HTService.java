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
 * Metodos que nescessitam tando do HistoricoService quanto do TarefaService.
 *
 * @author MGaukken__
 */
@Service
public class HTService {

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TarefaService tarefaService;

    /**
     * Move uma tarefa da aba de tarefas para o historico, de acordo com o id da
     * tarefa.
     *
     * @param id
     * @return
     */
    public boolean moverParaHistorico(Integer id) {
        Tarefa tarefa = tarefaService.getTarefaById(id);
        if (tarefa != null) {
            Historico historico = new Historico(
                    historicoService.findMissingId(historicoService.findAllIds()),
                    tarefa.getUserId(),
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

    /**
     * Move uma tarefa do historico para a aba de tarefas, de acordo com o id da
     * tarefa.
     *
     * @param id
     * @return
     */
    public boolean moverParaTarefas(Integer id) {
        Historico historico = historicoService.getTarefaById(id);
        if (historico != null) {
            Tarefa tarefa = new Tarefa(
                    tarefaService.findMissingId(tarefaService.findAllIds()),
                    historico.getUserId(),
                    historico.getTitulo(),
                    historico.getDatat(),
                    historico.getDescricao(),
                    "ativa"
            );

            tarefaService.criarTarefa(tarefa);

            return historicoService.deletarTarefa(id);
        }
        return false;
    }

}
