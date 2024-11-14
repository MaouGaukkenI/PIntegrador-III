/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.atividades.service;

import com.senac.atividades.data.Historico;
import com.senac.atividades.data.Tarefa;
import static com.senac.atividades.service.TarefaService.getTarefas;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author MGaukken__
 */
@Service
public class HistoricoService {

    private static List<Historico> historicos = new ArrayList<>();

    /**
     * Cria a lista com as tarefas para teste!
     */
    public HistoricoService() {
        historicos.add(new Historico(1, 1, "atividade1", "2005-07-11", "Atividade teste 1", "Finalizada"));
        historicos.add(new Historico(2, 1, "atividade2", "2005-07-11", "Atividade teste 2", "Finalizada"));
        historicos.add(new Historico(3, 1, "atividade3", "2005-07-11", "Atividade teste 3", "Finalizada"));
        historicos.add(new Historico(4, 1, "atividade4", "2005-07-12", "Atividade teste 4", "Finalizada"));
        historicos.add(new Historico(5, 1, "atividade5", "2005-07-12", "Atividade teste 5", "Finalizada"));
        historicos.add(new Historico(6, 1, "atividade6", "2005-07-12", "Atividade teste 6", "Finalizada"));
        historicos.add(new Historico(7, 1, "atividade7", "2005-07-13", "Atividade teste 7", "Finalizada"));
        historicos.add(new Historico(8, 1, "atividade8", "2005-07-13", "Atividade teste 8", "Finalizada"));
        historicos.add(new Historico(9, 1, "atividade9", "2005-07-13", "Atividade teste 9", "Finalizada"));
        historicos.add(new Historico(10, 1, "atividade10", "2005-07-14", "Atividade teste 10", "Finalizada"));
        historicos.add(new Historico(11, 1, "atividade11", "2005-07-14", "Atividade teste 11", "Finalizada"));
        historicos.add(new Historico(12, 1, "atividade12", "2005-07-14", "Atividade teste 12", "Finalizada"));
        historicos.add(new Historico(13, 1, "atividade13", "2005-07-15", "Atividade teste 13", "Finalizada"));
        historicos.add(new Historico(14, 1, "atividade14", "2005-07-15", "Atividade teste 14", "Finalizada"));
        historicos.add(new Historico(15, 1, "atividade15", "2005-07-15", "Atividade teste 15", "Finalizada"));
        historicos.add(new Historico(16, 1, "atividade16", "2005-07-16", "Atividade teste 16", "Finalizada"));
        historicos.add(new Historico(17, 1, "atividade17", "2005-07-16", "Atividade teste 17", "Finalizada"));
        historicos.add(new Historico(18, 1, "atividade18", "2005-07-16", "Atividade teste 18", "Finalizada"));
        historicos.add(new Historico(19, 1, "atividade19", "2005-07-17", "Atividade teste 19", "Finalizada"));
        historicos.add(new Historico(20, 1, "atividade20", "2005-07-17", "Atividade teste 20", "Finalizada"));
        historicos.add(new Historico(21, 1, "atividade21", "2005-07-17", "Atividade teste 21", "Finalizada"));
        historicos.add(new Historico(22, 1, "atividade22", "2005-07-18", "Atividade teste 22", "Finalizada"));
        historicos.add(new Historico(23, 1, "atividade23", "2005-07-18", "Atividade teste 23", "Finalizada"));
        historicos.add(new Historico(24, 1, "atividade24", "2005-07-18", "Atividade teste 24", "Finalizada"));
        historicos.add(new Historico(25, 1, "atividade25", "2005-07-19", "Atividade teste 25", "Finalizada"));
        historicos.add(new Historico(26, 1, "atividade26", "2005-07-19", "Atividade teste 26", "Finalizada"));
        historicos.add(new Historico(27, 1, "atividade27", "2005-07-19", "Atividade teste 27", "Finalizada"));
        historicos.add(new Historico(28, 1, "atividade28", "2005-07-20", "Atividade teste 28", "Finalizada"));
        historicos.add(new Historico(29, 1, "atividade29", "2005-07-20", "Atividade teste 29", "Finalizada"));
        historicos.add(new Historico(30, 1, "atividade30", "2005-07-20", "Atividade teste 30", "Finalizada"));
        historicos.add(new Historico(31, 1, "atividade31", "2005-07-21", "Atividade teste 31", "Finalizada"));
        historicos.add(new Historico(32, 1, "atividade32", "2005-07-21", "Atividade teste 32", "Finalizada"));
        historicos.add(new Historico(33, 1, "atividade33", "2005-07-21", "Atividade teste 33", "Finalizada"));
        historicos.add(new Historico(34, 1, "atividade34", "2005-07-22", "Atividade teste 34", "Finalizada"));
        historicos.add(new Historico(35, 1, "atividade35", "2005-07-22", "Atividade teste 35", "Finalizada"));
        historicos.add(new Historico(36, 1, "atividade36", "2005-07-22", "Atividade teste 36", "Finalizada"));
        historicos.add(new Historico(37, 1, "atividade37", "2005-07-23", "Atividade teste 37", "Finalizada"));
        historicos.add(new Historico(38, 1, "atividade38", "2005-07-23", "Atividade teste 38", "Finalizada"));
        historicos.add(new Historico(39, 1, "atividade39", "2005-07-23", "Atividade teste 39", "Finalizada"));
        historicos.add(new Historico(40, 1, "atividade40", "2005-07-24", "Atividade teste 40", "Finalizada"));

    }

    /**
     * Cria uma lista com todas tarefas presentes no historico.
     *
     * @return Lista retornada.
     */
    public List<Historico> listarHistorico() {
        List<Historico> his = getHistoricos();
        return his;
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
        List<Integer> ids = new ArrayList<>();
        for (Historico his : getHistoricos()) {
            ids.add(his.getId());
        }
        return ids;
    }

    /**
     * Retorna uma tarefa do historico de acordo com o id enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return uma tarefa com o id enviado.
     */
    public Historico getTarefaById(Integer id) {
        for (Historico his : historicos) {
            if (Objects.equals(id, his.getId())) {
                return his;
            }
        }
        return null;
    }

    /**
     * Permite criar uma tarefa no historico.
     *
     * @param his entidade de formatação.
     * @return a tarefa que foi adicionada em JSON.
     */
    public Historico criarTarefa(Historico his) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            his.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            his.setId(nextId);
        }

        getHistoricos().add(his);
        return his;
    }

    /**
     * Permite criar uma tarefa no historico utilizando o metodo GET.
     *
     * @param his entidade de formatação
     * @param idUser id do usuario.
     * @param tit titulo da tarefa.
     * @param dat data para finalização da tarefa.
     * @param des descrição da tarefa.
     * @param sta status da tarefa.
     * @return a tarefa que foi adicionada em JSON
     */
    public Historico cadAt(@Valid @RequestBody Historico his, Integer idUser, String tit, String dat, String des, String sta) {
        List<Integer> allIds = findAllIds();

        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            his.setId(missingId);
        } else {
            int nextid = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            his.setId(nextid);
        }

        his.setUserId(idUser);
        his.setTitulo(tit);
        his.setDatat(dat);
        his.setDescricao(des);
        his.setStatust(sta);

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
    public Historico editarTar(Integer id, Historico h) {
        Historico his = getTarefaById(id);

        if (his != null) {
            his.setDatat(h.getDatat());
            his.setDescricao(h.getDescricao());
            his.setStatust(h.getStatust());
            his.setTitulo(h.getTitulo());

            return his;
        }
        return null;
    }

    /**
     * Permite excluir permanentemente uma tarefa do historico.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return um booleando com status de sucesso do metodo.
     */
    public boolean deletarTarefa(Integer id) {
        return historicos.removeIf(historico -> historico.getId().equals(id));
    }
    
    /**
     * Deleta todas tarefas com o id do usuario enviado.
     * @param id id do usuario
     * @return booleno com resultado da execução da função.
     */
    public boolean dellByUserID(Integer id) {
        return historicos.removeIf(historico -> historico.getUserId().equals(id));
    }

    /**
     * @return retorna o historico
     */
    public static List<Historico> getHistoricos() {
        return historicos;
    }

    /**
     * @param aHistoricos define o historico
     */
    public static void setHistoricos(List<Historico> aHistoricos) {
        historicos = aHistoricos;
    }

}
