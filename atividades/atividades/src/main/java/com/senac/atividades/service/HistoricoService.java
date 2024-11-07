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
 * @author TheDe
 */
@Service
public class HistoricoService{
    private static List<Historico> historicos = new ArrayList<>();
    
    public HistoricoService(){
        historicos.add(new Historico(1,"atividade1","2005-07-11","Atividade teste 1", "Finalizada"));
        historicos.add(new Historico(2,"atividade2","2005-07-11","Atividade teste 2", "Finalizada"));
        historicos.add(new Historico(3,"atividade3","2005-07-11","Atividade teste 3", "Finalizada"));
    }
    
    public Integer findMissingId(List<Integer> ids) {
        int n = ids.size();
        for (int i = 1; i <= n + 1; i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return null;
    }
    
    public List<Integer> findAllIds(){
        List<Integer> ids = new ArrayList<>();
        for (Historico his: getHistoricos()){
            ids.add(his.getId());
        }
        return ids;
    }
    
    public Historico getTarefaById(Integer id){
        for(Historico his: historicos){
            if(Objects.equals(id, his.getId())){
                return his;
            }
        }
        return null;
    }
    
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
    
    public Historico cadAt(@Valid @RequestBody Historico his, String tit, String dat, String des, String sta){
        List<Integer> allIds = findAllIds();
        
        Integer missingId = findMissingId(allIds);
        
        if(missingId != null){
            his.setId(missingId);
        } else {
            int nextid = allIds.isEmpty() ? 1: allIds.stream().max(Integer::compare).get() + 1;
            his.setId(nextid);
        }
        
        his.setTitulo(tit);
        his.setDatat(dat);
        his.setDescricao(des);
        his.setStatust(sta);
        
        return his;
    }
    
    public Historico editarTar (Integer id, Historico h){
        Historico his = getTarefaById(id);
        
        if(his != null){
            his.setDatat(h.getDatat());
            his.setDescricao(h.getDescricao());
            his.setStatust(h.getStatust());
            his.setTitulo(h.getTitulo());
            
            return his;
        }
        return null;
    }
    
    public boolean deletarTarefa(Integer id){
        return historicos.removeIf(historico -> historico.getId().equals(id));
    }

    /**
     * @return the historicos
     */
    public static List<Historico> getHistoricos() {
        return historicos;
    }

    /**
     * @param aHistoricos the historicos to set
     */
    public static void setHistoricos(List<Historico> aHistoricos) {
        historicos = aHistoricos;
    }
    
}
