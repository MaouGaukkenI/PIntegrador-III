/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PI_Code;

/**
 * Classe objeto para criação da lista da tabela modelo
 * @author TheDe
 */
public class His {
    private String titulo;
    private String data;
    private String desc;
    private String status;
    
    /**
     * Cria um novo objeto com a classe His
     * 
     * @param t Titulo da tarefa
     * @param da Data limite para a tarefa
     * @param de Descrição da tarefa
     * @param s Status da tarefa
     */
    public His(String t, String da, String de, String s){
        setData(da);
        setDesc(de);
        setStatus(s);
        setTitulo(t);
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
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
