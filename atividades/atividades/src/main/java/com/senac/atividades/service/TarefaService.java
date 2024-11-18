package com.senac.atividades.service;

import com.senac.atividades.data.TarefaEntity;
import com.senac.atividades.data.TarefaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author MGaukken__
 */
@Service
public class TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    /**
     * Cria uma lista com todas tarefas presentes na aba de tarefas.
     *
     * @return Lista retornada.
     */
    public List<TarefaEntity> listarTarefas() {
        return tarefaRepository.findAll();
    }

    /**
     * Cria uma lista com as tarefas de um usuario especifico.
     *
     * @param id id do usuario para busca.
     * @return Lista retornada.
     */
    public List<TarefaEntity> listarTarefasByUId(Integer id) {
        return tarefaRepository.findTarefasByUserid(id);
    }

    /**
     * Retorna uma lista com todos os ids cadastrados na aba de tarefas.
     *
     * @return Lista retornada.
     */
    public List<Integer> findAllIds() {
        return tarefaRepository.findAllIds();
    }

    /**
     * Retorna uma tarefa da aba de tarefas de acordo com o id enviado.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return uma tarefa com o id enviado.
     */
    public TarefaEntity getTarefaById(Integer id) {
        return tarefaRepository.findTarefaById(id);
    }

    /**
     * Permite criar uma tarefa na aba de tarefas.
     *
     * @param tar entidade de formatação.
     * @return a tarefa que foi adicionada em JSON.
     */
    public TarefaEntity criarTarefa(TarefaEntity tar) {
        List<Integer> allIds = findAllIds();
        Integer missingId = findMissingId(allIds);

        if (missingId != null) {
            tar.setId(missingId);
        } else {
            int nextId = allIds.isEmpty() ? 1 : allIds.stream().max(Integer::compare).get() + 1;
            tar.setId(nextId);
        }

        tarefaRepository.save(tar);
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
    public TarefaEntity editarTar(Integer id, TarefaEntity t) {
        return tarefaRepository.findById(id).map(tarefaExistete -> {
            tarefaExistete.setTitulo(t.getTitulo());
            tarefaExistete.setDescricao(t.getDescricao());
            tarefaExistete.setDatat(t.getDatat());
            tarefaExistete.setStatust(t.getStatust());
            
            return tarefaRepository.save(tarefaExistete);
        }).orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o id: " + id));
    }

    /**
     * Permite excluir permanentemente uma tarefa da aba de tarefas.
     *
     * @param id utilizado para determinar qual tarefa foi selecionada.
     * @return
     */
    public boolean deletarTarefa(Integer id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true; // Tarefa deletada com sucesso
        }
        return false; // Tarefa não encontrada
    }

    /**
     * Deleta todas tarefas com o id do usuario enviado.
     *
     * @param id id do usuario
     * @return
     */
    @Transactional
    public boolean dellByUserID(Integer id) {
        // Verifica se o usuário possui tarefas associadas
        if (tarefaRepository.existsByUserid(id)) {
            tarefaRepository.deleteTarefasByUserid(id);
            return true; // Tarefas deletadas com sucesso
        }
        return false; // Nenhuma tarefa encontrada para o usuário
    }
}
