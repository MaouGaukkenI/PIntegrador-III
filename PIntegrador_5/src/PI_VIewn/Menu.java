/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PI_VIewn;

import PI_Code.His;
import PI_Code.Tar;
import PI_Code.newConexao;
import PI_Code.tblHis;
import PI_Code.tblTar;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.PlainDocument;

/**
 *
 * @author TheDe
 */
public class Menu extends javax.swing.JFrame {

    tblTar dtm = new tblTar();
    tblHis dtmH = new tblHis();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    newConexao conect = new newConexao();
    int i = 0;
    boolean state = false;

    Hist h;

    /**
     * Construtor da classe Menu. Este método inicializa e configura os
     * componentes iniciais do menu, como a tabela de tarefas e a visibilidade
     * de determinados elementos.
     *
     * Além disso, define um ouvinte de teclado para adicionar uma nova tarefa
     * quando a combinação de teclas Ctrl+A é pressionada. O método também
     * estabelece a conexão com o banco de dados, configura a classe de
     * histórico associada e carrega as tarefas do banco de dados na tabela.
     *
     * Por padrão, o componente lpExecute é definido como invisível. Ele também
     * verifica e atualiza as tarefas em atraso.
     *
     * Por fim, assegura que o JFrame é focável para receber eventos de teclado,
     * garantindo que ele tenha o foco quando a aplicação for iniciada.
     */
    public Menu() {
        initComponents();
        conect.setConexão();
        conect.setMenu(this);
        h = new Hist(this, conect);
        conect.setHist(h);
        conect.carregarTar();
        tblTar.setModel(dtm);
        lpExecute.setVisible(false);
        tarefasEmAtraso();
        addTarTest();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                conect.desconectar();
            }
        });

        // Certifica-se de que o JFrame é focável para que ele possa receber eventos de teclado
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Adiciona tarefas aleatórias ao pressionar as teclas Ctrl + A na classe
     * Menu. Após a adição da tarefa, os dados são atualizados na tabela.
     *
     * Ao pressionar as teclas Ctrl + A, este método incrementa um contador e
     * utiliza a conexão com o banco de dados para adicionar uma nova tarefa com
     * um título, data e descrição gerados aleatoriamente. A tarefa é marcada
     * como "Ativa".
     *
     * Após a adição da tarefa, o método pausa o programa por 100 milissegundos
     * para garantir que a adição seja concluída antes de atualizar os dados na
     * tabela.
     *
     * Se a adição da tarefa for bem-sucedida, o método chama o método
     * "tarefasEmAtraso()" para atualizar as tarefas na tabela, refletindo as
     * alterações feitas.
     */
    public void addTarTest() {
        state = false;
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                    i++;
                    if (conect.addTar("Titulo " + i, "01/01/202" + i, "Descrção foda " + i, "Ativa")) {
                        System.out.println("\nTarefa adicionada ao banco de dados");
                        state = true;

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            System.out.println("erro ao pausar programa, codigo de erro: " + ex.getMessage());
                        }

                        if (state) {
                            tarefasEmAtraso();
                        }
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    /**
     * Retorna a tabela modelo utilizada na classe Menu.
     *
     * @return tblHis modelo utilizada na classe
     */
    public tblTar getDTM() {
        return dtm;
    }

    /**
     * Atualiza o status das tarefas para refletir se estão em atraso ou ativas.
     * Percorre todas as linhas da tabela de tarefas e compara a data de cada
     * uma com a data atual. Se a data da tarefa for anterior à data atual, a
     * tarefa é marcada como "Em Atraso"; caso contrário, é marcada como
     * "Ativa".
     *
     * @return true se as tarefas foram atualizadas com sucesso, false caso
     * contrário
     */
    public boolean tarefasEmAtraso() {
        boolean check = false;
        UpdateNM();
        for (int a = 0; a < dtm.getRowCount(); a++) {
            try {
                Date data = sdf.parse((String) dtm.getValueAt(a, 1));
                Date dataAtual = new Date();
                String linha = conect.getIdS("tarefas", a);
                if ("-1".equals(linha)) {
                } else {
                    if (data.compareTo(dataAtual) < 0) {
                        if (conect.editTar("status", "Em Atraso", linha)) {
                            //System.err.println("Dados Atualizados");
                            check = true;
                        } else {
                            System.err.println("Problemas ao atualizar dados");
                            return false;
                        }
                    } else if (data.compareTo(dataAtual) > 0) {
                        if (conect.editTar("status", "Ativa", linha)) {
                            //System.err.println("Dados Atualizados");
                            check = true;
                        } else {
                            System.err.println("Problemas ao atualizar dados");
                            return false;
                        }
                    } else {
                        check = true;
                    }
                }
            } catch (ParseException ex) {
                System.out.println("Erro ao converter data: " + ex.getMessage());
                return false;
            }
        }

        if (check) {
            Update();
            System.err.println("Dados Atualizados");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Atualiza o status das tarefas para refletir se estão em atraso ou ativas.
     * Percorre todas as linhas da tabela de tarefas e compara a data de cada
     * uma com a data atual. Se a data da tarefa for anterior à data atual, a
     * tarefa é marcada como "Em Atraso"; caso contrário, é marcada como
     * "Ativa".
     *
     * Não envia mensagens no console duarante a execução
     *
     * @return true se as tarefas foram atualizadas com sucesso, false caso
     * contrário
     */
    public boolean tarefasEmAtrasoNotMensage() {
        boolean check = false;
        for (int a = 0; a < dtm.getRowCount(); a++) {
            try {
                Date data = sdf.parse((String) dtm.getValueAt(a, 1));
                Date dataAtual = new Date();
                String linha = conect.getIdS("tarefas", a);
                if ("-1".equals(linha)) {
                } else {
                    if (data.compareTo(dataAtual) < 0) {
                        if (conect.editTar("status", "Em Atraso", linha)) {
                            check = true;
                        } else {
                            return false;
                        }
                    } else if (data.compareTo(dataAtual) > 0) {
                        if (conect.editTar("status", "Ativa", linha)) {
                            check = true;
                        } else {
                            return false;
                        }
                    } else {
                        check = true;
                    }
                }
            } catch (ParseException ex) {
                return false;
            }
        }

        if (check) {
            UpdateNM();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Atualiza os dados da tabela de tarefas. Este método é responsável por
     * atualizar os dados exibidos na tabela de tarefas, garantindo que
     * quaisquer alterações recentes sejam refletidas visualmente. Ele aciona a
     * notificação de mudança de dados no modelo da tabela (dtm), em seguida,
     * atualiza o modelo da tabela (tblTar.setModel(dtm)) para garantir que as
     * mudanças sejam exibidas corretamente.
     */
    public void Update() {
        System.err.println("\nAtualizando dados");

        dtm.removeAllRows();
        conect.setMenu(this);
        conect.carregarTar();
        dtm.fireTableDataChanged();
        tblTar.setModel(dtm);
    }

    /**
     * Atualiza os dados da tabela de tarefas. Este método é responsável por
     * atualizar os dados exibidos na tabela de tarefas, garantindo que
     * quaisquer alterações recentes sejam refletidas visualmente. Ele aciona a
     * notificação de mudança de dados no modelo da tabela (dtm), em seguida,
     * atualiza o modelo da tabela (tblTar.setModel(dtm)) para garantir que as
     * mudanças sejam exibidas corretamente.
     *
     * Não encia notificações no console durante a execução
     */
    public void UpdateNM() {
        dtm.removeAllRows();
        conect.setMenu(this);
        conect.updateTar();
        dtm.fireTableDataChanged();
        tblTar.setModel(dtm);
    }

    /**
     * Atualiza os campos de texto com os dados da linha selecionada na tabela
     * de tarefas. Obtém o título, a data e a descrição da tarefa selecionada na
     * tabela e os exibe nos campos de texto correspondentes.
     */
    public void capDados() {
        txtTitulo.setText((String) dtm.getValueAt(tblTar.getSelectedRow(), 0));
        txtData.setText((String) dtm.getValueAt(tblTar.getSelectedRow(), 1));
        txtDesc.setText((String) dtm.getValueAt(tblTar.getSelectedRow(), 2));
    }

    /**
     * Atualiza uma tarefa no banco de dados.
     *
     * Este método é responsável por editar os detalhes de uma tarefa no banco
     * de dados, incluindo o título, a data e a descrição. Os parâmetros a, b e
     * c representam respectivamente o novo título, a nova data e a nova
     * descrição da tarefa a ser editada.
     *
     * Em seguida chama o metodo responsavel pela atualização dos dados.
     *
     * @param a Novo título da tarefa
     * @param b Nova data da tarefa no formato "dd/mm/aaaa"
     * @param c Nova descrição da tarefa
     * @param row id da linha na tabela onde a edição será realizada
     */
    public void edit(String a, String b, String c, int row) {
        conect.editTar("titulo", a, conect.getIdS("tarefas", row));
        conect.editTar("data", b, conect.getIdS("tarefas", row));
        conect.editTar("descricao", c, conect.getIdS("tarefas", row));
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println("erro ao pausar programa, codigo de erro: " + ex.getMessage());
        }
        tarefasEmAtraso();
    }

    /**
     * Cria uma nova tarefa e a adiciona à tabela tblTar.
     *
     * Este método recebe o título, a data e a descrição da tarefa como
     * parâmetros e os utiliza para criar uma nova entrada na tabela de tarefas.
     * Após adicionar a tarefa, verifica se a operação foi bem-sucedida e, em
     * caso afirmativo, atualiza a exibição das tarefas em atraso na interface
     * gráfica.
     *
     * @param a O título da nova tarefa.
     * @param b A data da nova tarefa.
     * @param c A descrição da nova tarefa.
     */
    public void add(String a, String b, String c) {
        if (conect.addTar(a, b, c, "Ativa")) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("erro ao pausar programa, codigo de erro: " + ex.getMessage());
            }
            tarefasEmAtraso();
        }
    }

    /**
     * Limpa os capos de texto para criar uma nova tarefa.
     *
     */
    public void clear() {
        String a = "";
        txtData.setText(a);
        txtDesc.setText(a);
        txtTitulo.setText(a);
    }

    /**
     * Formata a data enviada para o formato dd/mm/AAAA.
     *
     * @return retorna a data formatada no formato dd/mm/AAAA
     */
    public String formatarData() {
        // Remover qualquer caractere não numérico
        String data = txtData.getText();
        String numeros = data.replaceAll("[^0-9]", "");

        // Verificar se restaram 8 números
        if (numeros.length() == 8) {
            // Formatar para dd/mm/aaaa
            return numeros.substring(0, 2) + "/" + numeros.substring(2, 4) + "/" + numeros.substring(4);
        } else {
            // Se não houver 8 números, retornar a data original
            return "";
        }
    }

    /**
     * Capita a linha selecionada da tabela.
     *
     * @return retorna a posição da linha selecionada em um formato int
     */
    public int getSRow() {
        return tblTar.getSelectedRow();
    }

    /**
     * Este método é chamado de dentro do construtor para inicializar o
     * formulário. ATENÇÃO: Não modifique este código. O conteúdo deste método é
     * sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        setTitle("DailyTasks app Menu");
        lpMenu = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTar = new javax.swing.JTable();
        btnEnd = new javax.swing.JButton();
        btnEdit_ = new javax.swing.JButton();
        btnAdd_ = new javax.swing.JButton();
        btnHis = new javax.swing.JButton();
        lpExecute = new javax.swing.JLayeredPane();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        lpMenu.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Menu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        jScrollPane1.setToolTipText("Tabela de tarefas");

        tblTar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblTar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        tblTar.setToolTipText("Tabela de tarefas");
        tblTar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTar);

        btnEnd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEnd.setText("Finalizar Tarefa");
        btnEnd.setToolTipText("Encia uma tarefa para o estorico, com o status de concluida");
        btnEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndActionPerformed(evt);
            }
        });

        btnEdit_.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit_.setText("Editar Tarefa");
        btnEdit_.setToolTipText("Abre o menu de edição dos dados da tarefa");
        btnEdit_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit_ActionPerformed(evt);
            }
        });

        btnAdd_.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAdd_.setText("Criar Tarefa");
        btnAdd_.setToolTipText("Abre o Menu responsavel por criar uma nova tarefa");
        btnAdd_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_ActionPerformed(evt);
            }
        });

        btnHis.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnHis.setText("Histórico");
        btnHis.setToolTipText("Abre o historico de tarefas");
        btnHis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHisActionPerformed(evt);
            }
        });

        lpMenu.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpMenu.setLayer(btnEnd, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpMenu.setLayer(btnEdit_, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpMenu.setLayer(btnAdd_, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpMenu.setLayer(btnHis, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout lpMenuLayout = new javax.swing.GroupLayout(lpMenu);
        lpMenu.setLayout(lpMenuLayout);
        lpMenuLayout.setHorizontalGroup(
            lpMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lpMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lpMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(lpMenuLayout.createSequentialGroup()
                        .addComponent(btnEnd)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit_)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd_)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHis)))
                .addContainerGap())
        );
        lpMenuLayout.setVerticalGroup(
            lpMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lpMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lpMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnd)
                    .addComponent(btnEdit_)
                    .addComponent(btnAdd_)
                    .addComponent(btnHis))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lpExecute.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), ".", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAdd.setText("Criar Tarefa");
        btnAdd.setToolTipText("Cria uma nova tarefa com os dados inseridos nos camposa acima");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit.setText("Editar Tarefa");
        btnEdit.setToolTipText("Edita uma tarefa com os dados inseridos nos camposa acima");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Titulo:");
        jLabel1.setToolTipText("Campo de inserção do Titulo da tarefa");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Data:");
        jLabel2.setToolTipText("Campo de inserção da Data da tarefa");

        txtTitulo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtTitulo.setToolTipText("Campo de inserção do Titulo da tarefa");

        txtData.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtData.setToolTipText("Campo de inserção da Data da tarefa");

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        txtDesc.setToolTipText("Campo de inserção da Descrição da tarefa");
        jScrollPane2.setViewportView(txtDesc);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Descrição:");
        jLabel3.setToolTipText("Campo de inserção da Descrição da tarefa");

        lpExecute.setLayer(btnAdd, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(btnEdit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(txtTitulo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(txtData, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpExecute.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout lpExecuteLayout = new javax.swing.GroupLayout(lpExecute);
        lpExecute.setLayout(lpExecuteLayout);
        lpExecuteLayout.setHorizontalGroup(
            lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lpExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addContainerGap())
            .addGroup(lpExecuteLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        lpExecuteLayout.setVerticalGroup(
            lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lpExecuteLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lpExecuteLayout.createSequentialGroup()
                        .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(40, 40, 40)
                .addGroup(lpExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lpMenu)
                    .addComponent(lpExecute))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lpMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lpExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if ("".equals(formatarData())) {
            JOptionPane.showMessageDialog(null, "Data esta com o formato errado, tente novamente");
        } else {
            lpExecute.setVisible(false);
            edit(txtTitulo.getText(), formatarData(), txtDesc.getText(), getSRow());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if ("".equals(formatarData())) {
            JOptionPane.showMessageDialog(null, "Data esta com o formato errado, tente novamente");
        } else {
            lpExecute.setVisible(false);
            add(txtTitulo.getText(), formatarData(), txtDesc.getText());
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAdd_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_ActionPerformed
        clear();
        lpExecute.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Criar Tarefa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        btnAdd.setVisible(true);
        btnEdit.setVisible(false);
        lpExecute.setVisible(true);
    }//GEN-LAST:event_btnAdd_ActionPerformed

    private void btnEdit_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit_ActionPerformed
        clear();
        lpExecute.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Editar Tarefa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 18))); // NOI18N

        btnAdd.setVisible(false);
        btnEdit.setVisible(true);
        lpExecute.setVisible(true);
    }//GEN-LAST:event_btnEdit_ActionPerformed

    private void btnEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndActionPerformed
        try {
            Date data = sdf.parse((String) dtm.getValueAt(getSRow(), 1));
            Date dataAtual = new Date();
            String linha = conect.getIdS("tarefas", getSRow());
            if ("-1".equals(linha)) {
            } else {
                if (data.compareTo(dataAtual) < 0) {
                    String a = (String) dtm.getValueAt(getSRow(), 0);
                    String b = (String) dtm.getValueAt(getSRow(), 1);
                    String c = (String) dtm.getValueAt(getSRow(), 2);

                    conect.finalizarT(a, b, c, "Finalizada com Atrazo", conect.getIdI("tarefas", getSRow()));
                } else if (data.compareTo(dataAtual) > 0) {
                    String a = (String) dtm.getValueAt(getSRow(), 0);
                    String b = (String) dtm.getValueAt(getSRow(), 1);
                    String c = (String) dtm.getValueAt(getSRow(), 2);

                    conect.finalizarT(a, b, c, "Finalizada", conect.getIdI("tarefas", getSRow()));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        tarefasEmAtrasoNotMensage();
        Update();


    }//GEN-LAST:event_btnEndActionPerformed

    private void btnHisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHisActionPerformed
        h.setVisible(true);
        dispose();

        h.Update();
    }//GEN-LAST:event_btnHisActionPerformed

    private void tblTarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTarMouseClicked
        capDados();
    }//GEN-LAST:event_tblTarMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        tarefasEmAtrasoNotMensage();
    }//GEN-LAST:event_formKeyPressed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd_;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit_;
    private javax.swing.JButton btnEnd;
    private javax.swing.JButton btnHis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLayeredPane lpExecute;
    private javax.swing.JLayeredPane lpMenu;
    private javax.swing.JTable tblTar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
