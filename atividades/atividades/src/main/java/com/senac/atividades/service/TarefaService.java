package com.senac.atividades.service;

import com.senac.atividades.data.Tarefa;
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
public class TarefaService {

    private static List<Tarefa> tarefas = new ArrayList<>();

    /**
     * Cria a lista com as tarefas para teste!
     */
    public TarefaService() {
        tarefas.add(new Tarefa(1, 1, "atividade1", "2005-07-11", "Atividade teste 1", "ativa"));
        tarefas.add(new Tarefa(2, 1, "atividade2", "2005-07-11", "Atividade teste 2", "ativa"));
        tarefas.add(new Tarefa(3, 1, "atividade3", "2005-07-11", "Atividade teste 3", "ativa"));
        tarefas.add(new Tarefa(4, 1, "atividade4", "2005-07-11", "Atividade teste 4", "ativa"));
        tarefas.add(new Tarefa(5, 1, "atividade5", "2005-07-11", "Atividade teste 5", "ativa"));
        tarefas.add(new Tarefa(6, 1, "atividade6", "2005-07-11", "Atividade teste 6", "ativa"));
        tarefas.add(new Tarefa(7, 1, "atividade7", "2005-07-11", "Atividade teste 7", "ativa"));
        tarefas.add(new Tarefa(8, 1, "atividade8", "2005-07-11", "Atividade teste 8", "ativa"));
        tarefas.add(new Tarefa(9, 1, "atividade9", "2005-07-11", "Atividade teste 9", "ativa"));
        tarefas.add(new Tarefa(10, 1, "atividade10", "2005-07-11", "Atividade teste 10", "ativa"));
        tarefas.add(new Tarefa(11, 1, "atividade11", "2005-07-11", "Atividade teste 11", "ativa"));
        tarefas.add(new Tarefa(12, 1, "atividade12", "2005-07-11", "Atividade teste 12", "ativa"));
        tarefas.add(new Tarefa(13, 1, "atividade13", "2005-07-11", "Atividade teste 13", "ativa"));
        tarefas.add(new Tarefa(14, 1, "atividade14", "2005-07-11", "Atividade teste 14", "ativa"));
        tarefas.add(new Tarefa(15, 1, "atividade15", "2005-07-11", "Atividade teste 15", "ativa"));
        tarefas.add(new Tarefa(16, 1, "atividade16", "2005-07-11", "Atividade teste 16", "ativa"));
        tarefas.add(new Tarefa(17, 1, "atividade17", "2005-07-11", "Atividade teste 17", "ativa"));
        tarefas.add(new Tarefa(18, 1, "atividade18", "2005-07-11", "Atividade teste 18", "ativa"));
        tarefas.add(new Tarefa(19, 1, "atividade19", "2005-07-11", "Atividade teste 19", "ativa"));
        tarefas.add(new Tarefa(20, 1, "atividade20", "2005-07-11", "Atividade teste 20", "ativa"));
        tarefas.add(new Tarefa(21, 1, "atividade21", "2005-07-11", "Atividade teste 21", "ativa"));
        tarefas.add(new Tarefa(22, 1, "atividade22", "2005-07-11", "Atividade teste 22", "ativa"));
        tarefas.add(new Tarefa(23, 1, "atividade23", "2005-07-11", "Atividade teste 23", "ativa"));
        tarefas.add(new Tarefa(24, 1, "atividade24", "2005-07-11", "Atividade teste 24", "ativa"));
        tarefas.add(new Tarefa(25, 1, "atividade25", "2005-07-11", "Atividade teste 25", "ativa"));
        tarefas.add(new Tarefa(26, 1, "atividade26", "2005-07-11", "Atividade teste 26", "ativa"));
        tarefas.add(new Tarefa(27, 1, "atividade27", "2005-07-11", "Atividade teste 27", "ativa"));
        tarefas.add(new Tarefa(28, 1, "atividade28", "2005-07-11", "Atividade teste 28", "ativa"));
        tarefas.add(new Tarefa(29, 1, "atividade29", "2005-07-11", "Atividade teste 29", "ativa"));
        tarefas.add(new Tarefa(30, 1, "atividade30", "2005-07-11", "Atividade teste 30", "ativa"));
        tarefas.add(new Tarefa(31, 1, "atividade31", "2005-07-11", "Atividade teste 31", "ativa"));
        tarefas.add(new Tarefa(32, 1, "atividade32", "2005-07-11", "Atividade teste 32", "ativa"));
        tarefas.add(new Tarefa(33, 1, "atividade33", "2005-07-11", "Atividade teste 33", "ativa"));
        tarefas.add(new Tarefa(34, 1, "atividade34", "2005-07-11", "Atividade teste 34", "ativa"));
        tarefas.add(new Tarefa(35, 1, "atividade35", "2005-07-11", "Atividade teste 35", "ativa"));
        tarefas.add(new Tarefa(36, 1, "atividade36", "2005-07-11", "Atividade teste 36", "ativa"));
        tarefas.add(new Tarefa(37, 1, "atividade37", "2005-07-11", "Atividade teste 37", "ativa"));
        tarefas.add(new Tarefa(38, 1, "atividade38", "2005-07-11", "Atividade teste 38", "ativa"));
        tarefas.add(new Tarefa(39, 1, "atividade39", "2005-07-11", "Atividade teste 39", "ativa"));
        tarefas.add(new Tarefa(40, 1, "atividade40", "2005-07-11", "Atividade teste 40", "ativa"));
    }

    /**
     * Cria uma lista com todas tarefas presentes na aba de tarefas.
     *
     * @return Lista retornada.
     */
    public List<Tarefa> listarTarefas() {
        List<Tarefa> tar = getTarefas();
        return tar;
    }

    /**
     * Retorna uma lista com todos os ids cadastrados na aba de tarefas.
     *
     * @return Lista retornada.
     */
    public List<Integer> findAllIds() {
        List<Integer> ids = new ArrayList<>();
        for (Tarefa tarefa : getTarefas()) {
            ids.add(tarefa.getId());
        }
        return ids;
    }

    /**
     * Retorna uma tarefa da aba de tarefas de acordo com o id enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return uma tarefa com o id enviado.
     */
    public Tarefa getTarefaById(Integer id) {
        for (Tarefa tarefa : tarefas) {
            if (Objects.equals(id, tarefa.getId())) {
                return tarefa;
            }
        }
        return null;
    }

    /**
     * Permite criar uma tarefa na aba de tarefas.
     *
     * @param tar entidade de formatação.
     * @return a tarefa que foi adicionada em JSON.
     */
    public Tarefa criarTarefa(Tarefa tar) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            tar.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            tar.setId(nextId);
        }

        getTarefas().add(tar);
        return tar;
    }

    /**
     * Permite criar uma tarefa na aba de tarefas utilizando o metodo GET.
     *
     * @param tar entidade de formatação
     * @param idUser id do usuario.
     * @param tit titulo da tarefa.
     * @param dat data para finalização da tarefa.
     * @param des descrição da tarefa.
     * @param sta status da tarefa.
     * @return a tarefa que foi adicionada em JSON
     */
    public Tarefa cadAt(@Valid @RequestBody Tarefa tar, Integer idUser, String tit, String dat, String des, String sta) {
        List<Integer> allIds = findAllIds();

        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            tar.setId(missingId);
        } else {
            int nextid = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            tar.setId(nextid);
        }

        tar.setUserId(idUser);
        tar.setTitulo(tit);
        tar.setDatat(dat);
        tar.setDescricao(des);
        tar.setStatust(sta);

        return tar;
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
     * Permite que uma tarefa da aba de tarefas seja editada de acordo com o id
     * enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @param t entidade de formatação.
     * @return a tarefa editada em JSON.
     */
    public Tarefa editarTar(Integer id, Tarefa t) {
        Tarefa tar = getTarefaById(id);

        if (tar != null) {
            tar.setDatat(t.getDatat());
            tar.setDescricao(t.getDescricao());
            tar.setStatust(t.getStatust());
            tar.setTitulo(t.getTitulo());
        }
        System.out.println("Os dados enviados pelo json são:" + tar);
        return tar;
    }

    /**
     * Permite excluir permanentemente uma tarefa da aba de tarefas.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return um booleando com status de sucesso do metodo.
     */
    public boolean deletarTarefa(Integer id) {
        return tarefas.removeIf(tarefa -> tarefa.getId().equals(id));
    }
    
    /**
     * Deleta todas tarefas com o id do usuario enviado.
     * @param id id do usuario
     * @return booleno com resultado da execução da função.
     */
    public boolean dellByUserID(Integer id) {
        return tarefas.removeIf(tarefa -> tarefa.getUserId().equals(id));
    }

    /**
     * @return retorna as tareas
     */
    public static List<Tarefa> getTarefas() {
        return tarefas;
    }

    /**
     * @param aTarefas define as tarefas
     */
    public static void setTarefas(List<Tarefa> aTarefas) {
        tarefas = aTarefas;
    }
}
