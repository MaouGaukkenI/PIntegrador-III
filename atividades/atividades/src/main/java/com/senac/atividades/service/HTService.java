/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.data.HistoricoEntity;
import com.senac.atividades.data.TarefaEntity;
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
        TarefaEntity tarefa = tarefaService.getTarefaById(id);
        if (tarefa != null) {
            HistoricoEntity historico = new HistoricoEntity();
            historico.setUserid(tarefa.getUserid());
            historico.setTitulo(tarefa.getTitulo());
            historico.setDatat(tarefa.getDatat());
            historico.setDescricao(tarefa.getDescricao());
            historico.setStatust("Finalizada");

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
        HistoricoEntity historico = historicoService.getTarefaById(id);
        if (historico != null) {
            TarefaEntity tarefa = new TarefaEntity();
            tarefa.setUserid(historico.getUserid());
            tarefa.setTitulo(historico.getTitulo());
            tarefa.setDatat(historico.getDatat());
            tarefa.setDescricao(historico.getDescricao());
            tarefa.setStatust("ativa");

            tarefaService.criarTarefa(tarefa);

            return historicoService.deletarTarefa(id);
        }
        return false;
    }

}
