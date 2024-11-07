/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PI_VIewn;

import PI_Code.newConexao;
import PI_Code.tblHis;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TheDe
 */
public class Hist extends javax.swing.JFrame {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    tblHis dtm = new tblHis();
    newConexao conect;
    Menu m;

    /**
     * Construtor da classe Hist. Configura os componentes iniciais do menu,
     * como a tabela do histórico, e estabelece a conexão com o banco de dados.
     *
     * Este construtor inicializa os componentes gráficos da interface do
     * histórico, define a conexão com o banco de dados e carrega os dados do
     * histórico a partir dele. A tabela do histórico é configurada para exibir
     * os dados carregados.
     *
     * Além disso, define um listener para o evento de fechamento da janela, que
     * garante que a conexão com o banco de dados será encerrada adequadamente
     * ao fechar a janela do histórico.
     *
     * @param m_ O objeto do menu principal, necessário para o controle da
     * interface.
     * @param c A conexão com o banco de dados, responsável por carregar os
     * dados do histórico.
     */
    public Hist(Menu m_, newConexao c) {
        initComponents();
        this.conect = c;
        conect.setHist(this);
        conect.carregarHist();
        tblHists.setModel(dtm);
        this.m = m_;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                conect.desconectar();
            }
        });
    }

    /**
     * Atualiza os dados na tabela de histórico de podcasts.
     *
     * Este método remove todas as linhas existentes na tabela antes de carregar
     * os novos dados do banco de dados. Em seguida, ele solicita ao objeto de
     * conexão que carregue os dados atualizados do histórico. Após a conclusão
     * do carregamento, o método notifica a tabela para atualizar seus dados
     * visuais e exibe os dados recém-carregados na interface gráfica.
     *
     * Observação: Este método é responsável apenas pela atualização dos dados
     * na interface gráfica e não executa nenhuma operação de modificação nos
     * dados do banco de dados.
     *
     * @see Conexao#carregarHist()
     * @see DefaultTableModel#removeAllRows()
     * @see DefaultTableModel#fireTableDataChanged()
     */
    public void Update() {
        System.err.println("\nAtualizando dados");

        dtm.removeAllRows();
        conect.setHist(this);
        conect.carregarHist();
        dtm.fireTableDataChanged();
        tblHists.setModel(dtm);
    }

    /**
     * Retorna a tabela modelo utilizada na classe histórico
     *
     * @return tblHis modelo utilizada na classe
     */
    public tblHis getDTM() {
        return dtm;
    }

    /**
     * Capita a linha selecionada da tabela.
     *
     * @return retorna a posição da linha selecionada em um formato int
     */
    public int gl() {
        return tblHists.getSelectedRow();
    }

    /**
     * Este método é chamado de dentro do construtor para inicializar o
     * formulário. ATENÇÃO: Não modifique este código. O conteúdo deste método é
     * sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setTitle("DailyTasks app Histórico");
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHists = new javax.swing.JTable();
        btnDell = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnRecu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Histórico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 24))); // NOI18N

        jScrollPane1.setToolTipText("Tabela de tarefas");

        tblHists.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblHists.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHists.setToolTipText("Tabela de tarefas");
        jScrollPane1.setViewportView(tblHists);

        btnDell.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnDell.setText("Deletar");
        btnDell.setToolTipText("Remove uma tarefa do Historico e deleta ela permanentemente");
        btnDell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDellActionPerformed(evt);
            }
        });

        btnReturn.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnReturn.setText("Voltar");
        btnReturn.setToolTipText("Volta ao menu do App");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnRecu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRecu.setText("Recuperar");
        btnRecu.setToolTipText("Recupera a tabela de volta para a lista de tarefas.");
        btnRecu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnDell)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRecu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReturn)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDell)
                    .addComponent(btnReturn)
                    .addComponent(btnRecu))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnDellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDellActionPerformed
        if (tblHists.getSelectedRow() != -1) {
            conect.removeRow("historico", conect.getIdI("historico", tblHists.getSelectedRow()));
            Update();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha para ser deletada");
        }
    }//GEN-LAST:event_btnDellActionPerformed

    private void btnRecuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuActionPerformed
        int sRow = gl();
        if (sRow != -1) {
            try {
                Date data = sdf.parse((String) dtm.getValueAt(gl(), 1));
                Date dataAtual = new Date();
                String linha = conect.getIdS("tarefas", gl());
                if (data.compareTo(dataAtual) < 0) {
                    String a = (String) dtm.getValueAt(gl(), 0);
                    String b = (String) dtm.getValueAt(gl(), 1);
                    String c = (String) dtm.getValueAt(gl(), 2);

                    conect.recuperarT(a, b, c, "Em Atraso", conect.getIdI("historico", gl()));
                } else if (data.compareTo(dataAtual) > 0) {
                    String a = (String) dtm.getValueAt(gl(), 0);
                    String b = (String) dtm.getValueAt(gl(), 1);
                    String c = (String) dtm.getValueAt(gl(), 2);

                    conect.recuperarT(a, b, c, "Ativa", conect.getIdI("historico", gl()));
                }
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }

            Update();
            m.UpdateNM();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha para ser deletada");
        }
    }//GEN-LAST:event_btnRecuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Hist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            Menu m;
            newConexao c;

            public void run() {
                new Hist(m, c).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDell;
    private javax.swing.JButton btnRecu;
    private javax.swing.JButton btnReturn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHists;
    // End of variables declaration//GEN-END:variables
}
