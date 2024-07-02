/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PI_Code;

import PI_VIewn.Hist;
import PI_VIewn.Menu;
import com.mysql.cj.jdbc.Driver;
import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TheDe
 */
public class newConexao {

    private String user = "Dayli";
    private String senha = "12983476";
    private String url = "jdbc:mysql://localhost:3306/daylitask";
    public java.sql.Connection thor = null;
    public java.sql.Connection conn = null;
    public Menu menu;
    public Hist hist;
    public PreparedStatement ps;
    public ResultSet rs;

    /**
     * Define o menu associado a esta instância.
     *
     * Este método atribui um objeto do tipo Menu à instância atual, permitindo
     * que ela acesse e interaja com os elementos do menu. O menu fornecido é
     * usado para configurar as opções disponíveis e as ações a serem executadas
     * quando essas opções são selecionadas pelo usuário.
     *
     * @param menu_ O menu a ser associado a esta instância.
     */
    public void setMenu(Menu menu_) {
        this.menu = menu_;
    }

    /**
     * Define o historico associado a esta instância.
     *
     * Este método atribui um objeto do tipo Hist à instância atual, permitindo
     * que ela acesse e interaja com os elementos do historico. O historico
     * fornecido é usado para configurar as opções disponíveis e as ações a
     * serem executadas quando essas opções são selecionadas pelo usuário.
     *
     * @param hist_ O historico a ser associado a esta instância.
     */
    public void setHist(Hist hist_) {
        this.hist = hist_;
    }

    /**
     * Este método estabelece uma conexão com o banco de dados MySQL.
     *
     * Primeiro, ele carrega o driver JDBC necessário para se conectar ao banco
     * de dados. Em seguida, cria uma conexão usando as credenciais fornecidas,
     * incluindo o URL do banco de dados, nome de usuário e senha. Após a
     * conexão ser estabelecida com sucesso, uma mensagem de boas-vindas é
     * exibida no console, indicando o nome de usuário que se conectou ao
     * aplicativo DailyTask.
     */
    public void setConexão() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            thor = DriverManager.getConnection(url, this.user, this.senha);
            System.out.println("Usuario " + user + " Seja bem vindo ao DailyTask!\n");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(newConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna a conexão com o banco de dados.
     *
     * Este método é responsável por retornar a conexão ativa com o banco de
     * dados, permitindo que outras classes a utilizem para executar consultas,
     * atualizações ou outras operações no banco de dados.
     *
     * @return A conexão ativa com o banco de dados.
     */
    public java.sql.Connection getConexão() {
        return thor;
    }

    /**
     * Fecha a conexão com o banco de dados, encerrando a sessão do usuário
     * atual. Isso libera recursos e encerra qualquer comunicação ativa com o
     * banco de dados.
     *
     * Antes de fechar a conexão, uma mensagem de despedida é exibida no
     * console, informando o nome do usuário que está sendo desconectado.
     */
    public void desconectar() {
        try {
            System.out.println("\nAdeus usuario " + user + " Até sua proxima visita!!");
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Não foi possivel finalizar a conexão, Código de erro: " + ex.getMessage());
        }
    }

    /**
     * Este método é responsável por carregar as tarefas do banco de dados e
     * exibi-las na tabela de tarefas. Ele primeiro verifica se a conexão com o
     * banco de dados está estabelecida e se a tabela de tarefas está
     * disponível. Em seguida, executa uma consulta SQL para selecionar todas as
     * tarefas ordenadas pelo status, com as tarefas em atraso primeiro. As
     * tarefas são então iteradas a partir do resultado da consulta e
     * adicionadas a uma lista de objetos Tar. Antes de adicionar uma tarefa à
     * lista, verifica se todos os campos da tarefa (título, data, descrição e
     * status) estão preenchidos. Se algum campo estiver vazio, a conexão com o
     * banco de dados é fechada e o método retorna false. Caso contrário, as
     * tarefas são adicionadas à lista e o indicador de dadosOk é marcado como
     * true. Se o número de linhas do resultado da consulta for zero, o método
     * imprime uma mensagem indicando que o banco de dados está vazio e retorna
     * true. Caso contrário, verifica novamente se há linhas no resultado da
     * consulta e adiciona as tarefas à lista. Após o término do processamento,
     * se os dados estiverem OK, a lista de tarefas é definida no modelo da
     * tabela e o método retorna true. Se houver problemas com os dados
     * carregados ou se ocorrer algum erro durante o processo, o método imprime
     * uma mensagem de erro, fecha a conexão com o banco de dados e retorna
     * false.
     *
     * @return true se as tarefas foram carregadas com sucesso e exibidas na
     * tabela; false caso contrário.
     */
    public boolean carregarTar() {
        if (p1t()) {
            tblTar tp = menu.getDTM();
            boolean dadosOk = false;
            try {
                String c = "SELECT * FROM tarefas ORDER BY (status = 'Em Atraso') DESC";
                ps = conn.prepareStatement(c);
                rs = ps.executeQuery();

                List<Tar> defData = new ArrayList<>();

                System.err.println("Dados enviados a tabela");

                int numLinhas = 0;

                while (rs.next()) {
                    numLinhas++;

                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        desconectar();
                        return false;
                    } else {
                        Tar h = new Tar(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }

                if (numLinhas == 0) {
                    System.out.println("O banco de dados esta vazio, insira os dados");
                    return true;
                }

                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        System.out.println("Seu banco de dados possui dados corrompidos.");
                        desconectar();
                        return true;
                    } else {
                        Tar h = new Tar(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }

                if (dadosOk) {
                    tp.setList(defData);
                    return true;
                } else {
                    System.err.println("Problemas com os dados carregados, reinicie seu banco de dados e tente novamente");
                    desconectar();
                    return false;
                }

            } catch (SQLException ex) {
                System.out.println("Erro ao carregar tarefas, codigo do erro: " + ex.getMessage());
                desconectar();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Este método é responsável por carregar as tarefas do banco de dados e
     * exibi-las na tabela de tarefas. Ele primeiro verifica se a conexão com o
     * banco de dados está estabelecida e se a tabela de tarefas está
     * disponível. Em seguida, executa uma consulta SQL para selecionar todas as
     * tarefas ordenadas pelo status, com as tarefas em atraso primeiro. As
     * tarefas são então iteradas a partir do resultado da consulta e
     * adicionadas a uma lista de objetos Tar. Antes de adicionar uma tarefa à
     * lista, verifica se todos os campos da tarefa (título, data, descrição e
     * status) estão preenchidos. Se algum campo estiver vazio, a conexão com o
     * banco de dados é fechada e o método retorna false. Caso contrário, as
     * tarefas são adicionadas à lista e o indicador de dadosOk é marcado como
     * true. Se o número de linhas do resultado da consulta for zero o metodo
     * retorna true. Caso contrário, verifica novamente se há linhas no
     * resultado da consulta e adiciona as tarefas à lista. Após o término do
     * processamento, se os dados estiverem OK, a lista de tarefas é definida no
     * modelo da tabela e o método retorna true. Se houver problemas com os
     * dados carregados ou se ocorrer algum erro durante o processo, fecha a
     * conexão com o banco de dados e retorna false.
     *
     * obs: este metodo não envia mensagens, ele serve para atualizações
     * silenciosas.
     *
     * @return true se as tarefas foram carregadas com sucesso e exibidas na
     * tabela; false caso contrário.
     */
    public boolean updateTar() {
        if (p1t()) {
            tblTar tp = menu.getDTM();
            boolean dadosOk = false;
            try {
                String c = "SELECT * FROM tarefas ORDER BY (status = 'Em Atraso') DESC";
                ps = conn.prepareStatement(c);
                rs = ps.executeQuery();

                List<Tar> defData = new ArrayList<>();

                int numLinhas = 0;

                while (rs.next()) {
                    numLinhas++;
                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        desconectar();
                        return true;
                    } else {
                        Tar h = new Tar(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }
                if (numLinhas == 0) {
                    return true;
                }

                if (dadosOk) {
                    tp.setList(defData);
                    return true;
                } else {
                    desconectar();
                    return false;
                }

            } catch (SQLException ex) {
                desconectar();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Verifica se a conexão com o menu está estabelecida e, se estiver, prepara
     * os recursos de banco de dados.
     *
     * Este método é utilizado para garantir que a conexão com o menu esteja
     * ativa antes de executar operações no banco de dados. Se a conexão com o
     * menu for bem-sucedida, os recursos de banco de dados são preparados para
     * serem utilizados posteriormente. Caso contrário, uma mensagem de erro é
     * exibida e a conexão é encerrada.
     *
     * @return true se a conexão com o menu foi estabelecida e os recursos de
     * banco de dados foram preparados com sucesso, false caso contrário.
     */
    public boolean p1t() {
        if (menu != null) {
            ps = null;
            rs = null;
            conn = getConexão();
            return true;
        } else {
            System.err.println("Não foi possivel conectar ao Menu!");
            desconectar();
            return false;
        }
    }

    /**
     * Este método é responsável por editar uma tarefa no banco de dados. Ele
     * atualiza o valor de uma determinada coluna para a linha especificada na
     * tabela de tarefas.
     *
     * @param coluna A coluna que será atualizada.
     * @param valor O novo valor que será atribuído à coluna.
     * @param linha A linha (identificador único) da tarefa que será editada.
     * @return true se a tarefa foi editada com sucesso, false caso contrário.
     */
    public boolean editTar(String coluna, String valor, String linha) {
        if (p1Et()) {
            try {
                String c = "UPDATE tarefas SET " + coluna + " = ? WHERE id = ?";

                ps = conn.prepareStatement(c);

                ps.setString(1, valor);
                ps.setString(2, linha);

                int rowsAfected = ps.executeUpdate();

                if (rowsAfected > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao editar tarefa, codigo do erro: " + ex.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Este método verifica se a conexão com a classe de menu está ativa antes
     * de realizar uma edição.
     *
     * Se a conexão estiver ativa, o método redefine os objetos
     * PreparedStatement e ResultSet como nulos e obtém uma nova conexão com o
     * banco de dados. Em seguida, retorna verdadeiro para indicar que a
     * operação pode prosseguir.
     *
     * Se a conexão com a classe de menu estiver interrompida, o método exibe
     * uma mensagem de erro, desconecta-se do banco de dados e retorna falso
     * para indicar que a operação não pode ser concluída.
     *
     * @return true se a conexão com a classe de menu estiver ativa e a operação
     * puder prosseguir, false caso contrário
     */
    public boolean p1Et() {
        if (menu != null) {
            ps = null;
            rs = null;
            conn = getConexão();
            return true;
        } else {
            System.out.println("A conexão com a classe menu foi interrompida "
                    + "ao realizar uma edição");

            desconectar();
            return false;
        }
    }

    /**
     * Retorna o ID da linha especificada de uma tabela no banco de dados.
     *
     * Este método executa uma consulta SQL para recuperar o ID da linha
     * especificada da tabela fornecida. O parâmetro 'table' especifica o nome
     * da tabela e 'nLinha' especifica o índice da linha desejada (começando do
     * zero). O método utiliza o comando SQL 'ORDER BY' para garantir que as
     * linhas sejam recuperadas de forma consistente e, em seguida, usa 'LIMIT'
     * e 'OFFSET' para selecionar a linha específica desejada. Se o ID for
     * encontrado, ele é retornado como uma String. Caso contrário, o método
     * retorna "-1".
     *
     * @param table O nome da tabela no banco de dados.
     * @param nLinha O índice da linha desejada (começando do zero).
     * @return Uma String representando o ID da linha, ou "-1" se ocorrer um
     * erro ou se nenhum ID for encontrado.
     */
    public String getIdS(String table, int nLinha) {
        ps = null;
        rs = null;

        try {
            String c = "SELECT id FROM " + table + " ORDER BY id LIMIT 1 OFFSET " + nLinha;
            ps = conn.prepareStatement(c);
            rs = ps.executeQuery();

            String id = "-1";

            while (rs.next()) {
                id = rs.getString("id");
                //System.err.println("O id recuperado é: " + id);
            }

            return id;
        } catch (SQLException ex) {
            System.out.println("Erro ao carregat id, codigo do erro: " + ex.getMessage());
            return "-1";
        }
    }

    /**
     * Retorna o ID da linha especificada de uma tabela no banco de dados.
     *
     * Este método executa uma consulta SQL para recuperar o ID da linha
     * especificada da tabela fornecida. O parâmetro 'table' especifica o nome
     * da tabela e 'nLinha' especifica o índice da linha desejada (começando do
     * zero). O método utiliza o comando SQL 'ORDER BY' para garantir que as
     * linhas sejam recuperadas de forma consistente e, em seguida, usa 'LIMIT'
     * e 'OFFSET' para selecionar a linha específica desejada. Se o ID for
     * encontrado, ele é retornado como um int. Caso contrário, o método retorna
     * -1.
     *
     * @param table O nome da tabela no banco de dados.
     * @param nLinha O índice da linha desejada (começando do zero).
     * @return Um int representando o ID da linha, ou -1 se ocorrer um erro ou
     * se nenhum ID for encontrado.
     */
    public int getIdI(String table, int nLinha) {
        ps = null;
        rs = null;

        try {
            String c = "SELECT id FROM " + table + " ORDER BY id LIMIT 1 OFFSET " + nLinha;
            ps = conn.prepareStatement(c);
            rs = ps.executeQuery();

            int id = -1;

            while (rs.next()) {
                id = rs.getInt("id");
            }

            return id;
        } catch (SQLException ex) {
            System.out.println("Erro ao carregat id, codigo do erro: " + ex.getMessage());
            return -1;
        }
    }

    /**
     * Adiciona uma nova tarefa ao banco de dados.
     *
     * Este método recebe informações sobre o título, data, descrição e status
     * da tarefa a ser adicionada. Em seguida, executa uma instrução SQL para
     * inserir os dados fornecidos na tabela de tarefas do banco de dados.
     *
     * Antes de adicionar a tarefa, verifica se o usuário tem permissão para
     * realizar essa ação, verificando o status da permissão no menu principal.
     *
     * @param titulo O título da tarefa a ser adicionada.
     * @param data A data associada à tarefa no formato String.
     * @param desc A descrição da tarefa.
     * @param status O status atual da tarefa.
     * @return true se a tarefa foi adicionada com sucesso, false caso
     * contrário.
     */
    public boolean addTar(String titulo, String data, String desc, String status) {
        if (p1At()) {
            tblTar tp = menu.getDTM();
            try {
                String c = "INSERT INTO tarefas (titulo, data, descricao, status) VALUES (?,?,?,?)";
                ps = conn.prepareStatement(c, java.sql.Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, titulo);
                ps.setString(2, data);
                ps.setString(3, desc);
                ps.setString(4, status);

                int rowsAfected = ps.executeUpdate();

                if (rowsAfected > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao adicionar tarefa, codigo do erro: " + ex.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Este método verifica se a conexão com a classe de menu está ativa antes
     * de realizar uma operação de atualização.
     *
     * Se a conexão estiver ativa, o método configura os objetos
     * PreparedStatement e ResultSet para preparar uma consulta ao banco de
     * dados.
     *
     * Se a conexão estiver interrompida, o método imprime uma mensagem de erro
     * e encerra a conexão com o banco de dados.
     *
     * @return true se a conexão com a classe de menu estiver ativa e os objetos
     * PreparedStatement e ResultSet forem configurados com sucesso; false se a
     * conexão com a classe de menu estiver interrompida, resultando no
     * encerramento da conexão com o banco de dados.
     */
    public boolean p1At() {
        if (menu != null) {
            ps = null;
            rs = null;
            conn = getConexão();
            return true;
        } else {
            System.out.println("A conexão com a classe menu foi interrompida "
                    + "ao realizar uma adição de dados");

            desconectar();
            return false;
        }
    }

    /**
     * Carrega os dados do histórico a partir do banco de dados e atualiza a
     * tabela de histórico.
     *
     * Este método executa uma consulta SQL para recuperar os dados do histórico
     * do banco de dados. Os dados recuperados são então processados e
     * adicionados a uma lista de objetos His.
     *
     * Se o histórico estiver vazio, uma mensagem é exibida indicando que não há
     * dados no histórico. Se ocorrerem problemas ao carregar ou processar os
     * dados, uma mensagem de erro será exibida e o método retornará false.
     *
     * @return true se os dados do histórico forem carregados com sucesso e
     * atualizados na tabela, false caso contrário
     */
    public boolean carregarHist() {
        if (p1h()) {
            tblHis tp = hist.getDTM();
            boolean dadosOk = false;
            try {
                String c = "SELECT * FROM historico";
                ps = conn.prepareStatement(c);
                rs = ps.executeQuery();

                List<His> defData = new ArrayList<>();

                System.err.println("Dados enviados a tabela");

                int numLinhas = 0;

                while (rs.next()) {
                    numLinhas++;

                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        desconectar();
                        return false;
                    } else {
                        His h = new His(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }

                if (numLinhas == 0) {
                    System.out.println("O historico de dados esta vazio!");
                    return true;
                }

                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        System.out.println("Seu banco de dados possui dados corrompidos.");
                        desconectar();
                        return true;
                    } else {
                        His h = new His(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }

                if (dadosOk) {
                    tp.setList(defData);
                    return true;
                } else {
                    System.err.println("Problemas com os dados carregados, reinicie seu banco de dados e tente novamente");
                    desconectar();
                    return false;
                }

            } catch (SQLException ex) {
                System.out.println("Erro ao carregar tarefas, codigo do erro: " + ex.getMessage());
                desconectar();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Atualiza o histórico de podcasts com os dados do banco de dados. Este
     * método verifica se há novos registros no banco de dados e os adiciona ao
     * histórico de podcasts. Ele consulta a tabela "historico" no banco de
     * dados para obter informações sobre cada registro, incluindo título, data,
     * descrição e status.
     *
     * Se os dados forem obtidos com sucesso do banco de dados e estiverem
     * completos, eles são adicionados ao histórico de podcasts. Caso contrário,
     * a conexão com o banco de dados é encerrada e o método retorna false.
     *
     * obs: executa a busca de dados silenciosa sem envio de dados para o
     * usuario.
     *
     * @return true se o histórico for atualizado com sucesso, false caso
     * contrário.
     */
    public boolean updateHist() {
        if (p1h()) {
            tblHis tp = hist.getDTM();
            boolean dadosOk = false;
            try {
                String c = "SELECT * FROM historico";
                ps = conn.prepareStatement(c);
                rs = ps.executeQuery();

                List<His> defData = new ArrayList<>();

                int numLinhas = 0;

                while (rs.next()) {
                    numLinhas++;
                    String titulo = rs.getString("titulo");
                    String data = rs.getString("data");
                    String desc = rs.getString("descricao");
                    String status = rs.getString("status");

                    if (titulo == null || data == null || desc == null || status == null) {
                        desconectar();
                        return true;
                    } else {
                        His h = new His(titulo, data, desc, status);
                        defData.add(h);

                        dadosOk = true;
                    }
                }
                if (numLinhas == 0) {
                    return true;
                }

                if (dadosOk) {
                    tp.setList(defData);
                    return true;
                } else {
                    desconectar();
                    return false;
                }

            } catch (SQLException ex) {
                desconectar();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Este método verifica se há uma instância válida do objeto de histórico
     * (hist). Se o objeto de histórico não for nulo, a conexão com o banco de
     * dados é estabelecida e o método retorna true, indicando que a conexão foi
     * bem-sucedida. Caso contrário, uma mensagem de erro é exibida indicando
     * que a conexão não pôde ser estabelecida e o método retorna false. Além
     * disso, a conexão existente é encerrada.
     *
     * @return true se a conexão com o banco de dados for estabelecida com
     * sucesso, caso contrário, false.
     */
    public boolean p1h() {
        if (hist != null) {
            ps = null;
            rs = null;
            conn = getConexão();
            return true;
        } else {
            System.err.println("Não foi possivel conectar ao Menu!");
            desconectar();
            return false;
        }
    }

    /**
     * Este método adiciona um novo registro ao histórico, inserindo um título,
     * data, descrição e status.
     *
     * @param titulo O título do registro a ser adicionado.
     * @param data A data associada ao registro.
     * @param desc A descrição do registro.
     * @param status O status do registro.
     * @return true se o registro foi adicionado com sucesso, false caso
     * contrário.
     */
    public boolean addHis(String titulo, String data, String desc, String status) {
        if (p1At()) {
            tblHis tp = hist.getDTM();
            try {
                String c = "INSERT INTO historico (titulo, data, descricao, status) VALUES (?,?,?,?)";
                ps = conn.prepareStatement(c, java.sql.Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, titulo);
                ps.setString(2, data);
                ps.setString(3, desc);
                ps.setString(4, status);

                int rowsAfected = ps.executeUpdate();

                if (rowsAfected > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao finalizar, codigo do erro: " + ex.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Este método verifica se a conexão com a classe `Menu` está ativa antes de
     * realizar uma operação.
     *
     * Se a conexão estiver ativa, o método retorna verdadeiro e prepara a
     * conexão com o banco de dados para realizar a operação desejada.
     *
     * Caso contrário, se a conexão não estiver ativa, uma mensagem de erro é
     * exibida informando que a conexão foi interrompida durante a adição de
     * dados. Em seguida, o método desconecta da classe `Menu` e retorna falso.
     *
     * @return true se a conexão com a classe `Menu` estiver ativa, false caso
     * contrário.
     */
    public boolean p1Ah() {
        if (hist != null) {
            ps = null;
            rs = null;
            conn = getConexão();
            return true;
        } else {
            System.out.println("A conexão com a classe menu foi interrompida "
                    + "ao realizar uma adição de dados");

            desconectar();
            return false;
        }
    }

    /**
     * Finaliza uma tarefa, adicionando-a ao histórico e removendo-a da lista de
     * tarefas ativas.
     *
     * @param titulo O título da tarefa a ser finalizada.
     * @param data A data de conclusão da tarefa.
     * @param desc Uma descrição detalhada da tarefa.
     * @param status O status da tarefa após a conclusão.
     * @param rowDell O índice da linha na lista de tarefas ativas que deve ser
     * removida.
     */
    public void finalizarT(String titulo, String data, String desc, String status, int rowDell) {
        addHis(titulo, data, desc, status);

        removeRow("tarefas", rowDell);
    }

    /**
     * Este método é responsável por recuperar uma tarefa do histórico e
     * adicioná-la novamente à lista de tarefas ativas.
     *
     * Ele recebe como parâmetros o título, a data, a descrição e o status da
     * tarefa a ser recuperada, bem como a linha correspondente ao item a ser
     * removido da tabela de histórico.
     *
     * Primeiramente, a tarefa é adicionada à lista de tarefas ativas utilizando
     * o método {@link #addTar(String, String, String, String)}.
     *
     * Em seguida, a linha correspondente à tarefa no histórico é removida
     * utilizando o método {@link #removeRow(String, int)}, onde o primeiro
     * parâmetro é o nome da tabela (no caso, "historico") e o segundo parâmetro
     * é o índice da linha a ser removida.
     *
     * @param titulo O título da tarefa a ser recuperada.
     * @param data A data da tarefa a ser recuperada.
     * @param desc A descrição da tarefa a ser recuperada.
     * @param status O status da tarefa a ser recuperada.
     * @param rowDell O índice da linha correspondente ao item a ser removido da
     * tabela de histórico.
     */
    public void recuperarT(String titulo, String data, String desc, String status, int rowDell) {
        addTar(titulo, data, desc, status);

        removeRow("historico", rowDell);
    }

    /**
     * Este método é responsável por estabelecer uma conexão com o banco de
     * dados e inicializar os objetos de consulta e resultado.
     *
     * @return true se a conexão foi estabelecida com sucesso; false caso
     * contrário.
     */
    public boolean rRp1() {
        conn = getConexão();
        ps = null;
        rs = null;

        return true;
    }

    /**
     * Remove uma linha específica de uma tabela no banco de dados.
     *
     * Este método executa uma instrução SQL DELETE para remover uma linha da
     * tabela especificada. Ele recebe o nome da tabela e o número da linha como
     * parâmetros e, em seguida, constrói e executa uma consulta preparada para
     * excluir a linha com o ID correspondente ao número da linha fornecido.
     *
     * @param tabela o nome da tabela do banco de dados da qual a linha será
     * removida
     * @param row o número da linha a ser removida
     * @return true se a linha foi removida com sucesso, false caso contrário
     */
    public boolean removeRow(String tabela, int row) {
        if (rRp1()) {
            try {
                String c1 = " DELETE FROM ";
                String c2 = " WHERE id = ? ";

                ps = conn.prepareStatement(c1 + tabela + c2, java.sql.Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, row);

                int del = ps.executeUpdate();

                if (del > 0) {
                    System.out.println("Item excluido com sucesso");
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(newConexao.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }
}
