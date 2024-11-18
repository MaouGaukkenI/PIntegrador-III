package com.senac.atividades.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

/**
 * Serve como entidade para definir o formato dos dados armazenados.
 *
 * @author MGaukken__
 */
@Entity
@Table(name="Historico")
public class HistoricoEntity {

    @Id 
    private Integer id;
    
    private Integer userid;
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
     * @return the userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
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
}
