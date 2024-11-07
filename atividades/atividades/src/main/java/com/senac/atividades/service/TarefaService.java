
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
 * @author TheDe
 */
@Service
public class TarefaService {
    private static List<Tarefa> tarefas = new ArrayList<>();

    public TarefaService() {
        // Inicializa tarefas com dados estáticos
        tarefas.add(new Tarefa(1,"atividade1","2005-07-11","Atividade teste 1", "ativa"));
        tarefas.add(new Tarefa(2,"atividade2","2005-07-11","Atividade teste 2", "ativa"));
        tarefas.add(new Tarefa(3,"atividade3","2005-07-11","Atividade teste 3", "ativa"));
        tarefas.add(new Tarefa(4,"atividade4","2005-07-11","Atividade teste 4", "ativa"));
        tarefas.add(new Tarefa(5,"atividade5","2005-07-11","Atividade teste 5", "ativa"));
    }

    public List<Integer> findAllIds() {
        List<Integer> ids = new ArrayList<>();
        for (Tarefa tarefa : getTarefas()) {
            ids.add(tarefa.getId());
        }
        return ids;
    }
    
    public Tarefa getTarefaById(Integer id) {
        for (Tarefa tarefa : tarefas) {
            if (Objects.equals(id, tarefa.getId())) {
                return tarefa;
            }
        }
        return null; // Retorna null se a tarefa não for encontrada
    }

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

    public Tarefa cadAt(@Valid @RequestBody Tarefa ati,String tit, String dat, String des, String sta){
        List<Integer> allIds = findAllIds();
        
        Integer missingId = findMissingId(allIds);
        
        if(missingId != null){
            ati.setId(missingId);
        } else {
            int nextid = allIds.isEmpty() ? 1: allIds.stream().max(Integer::compare).get() + 1;
            ati.setId(nextid);
        }
        
        ati.setTitulo(tit);
        ati.setDatat(dat);
        ati.setDescricao(des);
        ati.setStatust(sta);
        
        return ati;
    }
        
    private Integer findMissingId(List<Integer> ids) {
        int n = ids.size();
        for (int i = 1; i <= n + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return null;
    }
    
    public Tarefa editarTar(Integer id, Tarefa t) {
        Tarefa tar = getTarefaById(id);

        if (tar != null) {
            tar.setDatat(t.getDatat());
            tar.setDescricao(t.getDescricao());
            tar.setStatust(t.getStatust());
            tar.setTitulo(t.getTitulo());
            return tar;
        }
        return null;
    }

    
    public boolean deletarTarefa(Integer id){
        return tarefas.removeIf(tarefa -> tarefa.getId().equals(id));
    }

    /**
     * @return the tarefas
     */
    public static List<Tarefa> getTarefas() {
        return tarefas;
    }

    /**
     * @param aTarefas the tarefas to set
     */
    public static void setTarefas(List<Tarefa> aTarefas) {
        tarefas = aTarefas;
    }
}
