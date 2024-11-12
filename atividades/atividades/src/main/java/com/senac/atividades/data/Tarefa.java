package com.senac.atividades.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author TheDe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    private Integer id;
    private Integer userId;
    private String titulo;
    private String datat;
    private String descricao;
    private String statust;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the datat
     */
    public String getDatat() {
        return datat;
    }

    /**
     * @param datat the datat to set
     */
    public void setDatat(String datat) {
        this.datat = datat;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the statust
     */
    public String getStatust() {
        return statust;
    }

    /**
     * @param statust the statust to set
     */
    public void setStatust(String statust) {
        this.statust = statust;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
