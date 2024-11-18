/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.data.HistoricoEntity;
import com.senac.atividades.data.HistoricoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author MGaukken__
 */
@Service
public class HistoricoService {

    @Autowired
    HistoricoRepository historicoRepository;

    /**
     * Cria uma lista com todas tarefas presentes no historico.
     *
     * @return Lista retornada.
     */
    public List<HistoricoEntity> listarHistorico() {
        return historicoRepository.findAll();
    }

    /**
     * Cria uma lista com as tarefas de um usuario especifico.
     *
     * @param id id do usuario para busca.
     * @return Lista retornada.
     */
    public List<HistoricoEntity> listarHistoricoByUId(Integer id) {
        return historicoRepository.findHistoricosByUserid(id);
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
     * Retorna uma lista com todos os ids cadastrados no historico.
     *
     * @return Lista retornada.
     */
    public List<Integer> findAllIds() {
        return historicoRepository.findAllIds();
    }

    /**
     * Retorna uma tarefa do historico de acordo com o id enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return uma tarefa com o id enviado.
     */
    public HistoricoEntity getTarefaById(Integer id) {
        return historicoRepository.findHistoricoById(id);
    }

    /**
     * Permite criar uma tarefa no historico.
     *
     * @param his entidade de formatação.
     * @return a tarefa que foi adicionada em JSON.
     */
    public HistoricoEntity criarTarefa(HistoricoEntity his) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            his.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            his.setId(nextId);
        }

        historicoRepository.save(his);
        return his;
    }

    /**
     * Permite que uma tarefa do historico seja editada de acordo com o id
     * enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @param h entidade de formatação.
     * @return a tarefa editada em JSON.
     */
    public HistoricoEntity editarTar(Integer id, HistoricoEntity h) {
        HistoricoEntity his = getTarefaById(id);

        if (his != null) {
            his.setDatat(h.getDatat());
            his.setDescricao(h.getDescricao());
            his.setStatust(h.getStatust());
            his.setTitulo(h.getTitulo());
        }
        System.out.println("Os dados enviados pelo json são:" + his);
        return his;
    }

    /**
     * Permite excluir permanentemente uma tarefa do historico.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return
     */
    public boolean deletarTarefa(Integer id) {
        if (historicoRepository.existsById(id)) {
            historicoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Deleta todas tarefas com o id do usuario enviado.
     *
     * @param id id do usuario.
     * @return 
     */
    @Transactional
    public boolean dellByUserID(Integer id) {
        // Verifica se o usuário possui tarefas associadas
        if (historicoRepository.existsByUserid(id)) {
            historicoRepository.deleteHistoricosByUserid(id);
            return true; // Tarefas deletadas com sucesso
        }
        return false; // Nenhuma tarefa encontrada para o usuário
    }

}
