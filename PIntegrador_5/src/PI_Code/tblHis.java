/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PI_Code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *Tabela modelo para o Histórico
 * @author TheDe
 */
public class tblHis extends DefaultTableModel{
    private static List <His> lista = new ArrayList<>();
    private String[] colunas = {"Titulo", "Data", "Descrição", "Status"};
    
    /**
     * Organiza a tabela, mantendo sempre as tabelas em atrazo na parte superior da tabela;
     * 
     */
    public void sort(){
        Comparator<His> comparador = Comparator.comparing((His a) -> a.getStatus().equals("Finalizada com atrazo") ? 0 : 1);
        lista.sort(comparador);
    }
    
    
    /**
    * Retorna o número de colunas na tabela modelo.
    *
    * @return O número de colunas na tabela modelo.
    * @author Gaukken
    */
    @Override
    public int getColumnCount(){
        return colunas.length;
    }
    
    /**
    * Retorna o número de linhas na tabela modelo.
    *
    * @return O número de linhas na tabela modelo.
    * @author Gaukken
    */
    @Override
    public int getRowCount() {
        return lista.size();
    }
    /**
    * Define uma nova lista de dados para a tabela modelo.
    *
    * @param lista_ A nova lista de dados para a tabela modelo.
    * @author Gaukken
    */
    public void setList(List<His> lista_) {
        lista = lista_;
    }

    /**
    * Retorna a lista de dados da tabela modelo.
    *
    * @return A lista de dados da tabela modelo.
    * @author Gaukken
    */
    public List<His> getList() {
        return lista;
    }

    /**
    * Retorna o nome da coluna com base no índice da coluna.
    *
    * @param columnIndex O índice da coluna cujo nome deve ser retornado.
    * @return O nome da coluna com base no índice fornecido.
    * @throws IndexOutOfBoundsException Se o índice da coluna estiver fora dos limites do array de colunas.
    * @author Gaukken
    */
    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    /**
    * Adiciona uma nova linha de dados à lista da tabela modelo.
    *
    * @param add Os dados a serem adicionados à lista da tabela modelo.
    * @author Gaukken
    */
    public static void addRow(His add) {
        lista.add(add);
        System.err.println("Dados adicionados ao Histórico");
    }

    /**
    * Remove a linha de dados especificada da lista da tabela modelo.
    *
    * @param row O índice da linha a ser removida da lista da tabela modelo.
    * @throws IndexOutOfBoundsException Se o índice da linha estiver fora dos limites da lista.
    * @author Gaukken
    */
    @Override
    public void removeRow(int row) {
        lista.remove(row);
    }

    /**
    * Remove todas as linhas de dados da lista da tabela modelo.
    *
    * @author Gaukken
    */
    public static void removeAllRows() {
        lista.clear();
    }

    /**
    * Retorna o valor na célula especificada da tabela modelo.
    *
    * @param linha O índice da linha da célula desejada.
    * @param coluna O índice da coluna da célula desejada.
    * @return O valor na célula especificada da tabela modelo.
    * @throws IndexOutOfBoundsException Se os índices da linha ou coluna estiverem fora dos limites da tabela.
    * @author Gaukken
    */
    @Override
    public Object getValueAt(int linha, int coluna) {
        if (linha >= 0 && linha < getRowCount() && coluna >= 0 && coluna < getColumnCount()) {
            His capDados = lista.get(linha);

            switch (coluna) {
                case 0:
                    return capDados.getTitulo();
                case 1:
                    return capDados.getData();
                case 2:
                    return capDados.getDesc();
                case 3:
                    return capDados.getStatus();

                default:
                    throw new IndexOutOfBoundsException("Coluna inválida");
            }
        } else {
            throw new IndexOutOfBoundsException("Linha ou coluna inválida");
        }
    }

    /**
    * Define o valor na célula especificada da tabela modelo.
    *
    * @param value O novo valor a ser definido na célula.
    * @param linha O índice da linha da célula a ser definida.
    * @param coluna O índice da coluna da célula a ser definida.
    * @throws IndexOutOfBoundsException Se os índices da linha ou coluna estiverem fora dos limites da tabela.
    * @throws ClassCastException Se o tipo do valor não for compatível com o tipo de dados da célula.
    * @author Gaukken
    */
    @Override
    public void setValueAt(Object value, int linha, int coluna) {
        if (linha >= 0 && linha < getRowCount() && coluna >= 0 && coluna < getColumnCount()) {
            His defDados = lista.get(linha);

            switch (coluna) {
                case 0:
                    defDados.setTitulo((String) value);
                    break;
                case 1:
                    defDados.setData((String) value);
                    break;
                case 2:
                    defDados.setDesc((String) value);
                    break;
                case 3:
                    defDados.setStatus((String) value);
                    break;

                default:
                    throw new IndexOutOfBoundsException("Coluna inválida");
            }
        } else {
            throw new IndexOutOfBoundsException("Linha ou coluna inválida");
        }
    }
}
