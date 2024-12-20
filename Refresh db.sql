-- Pode ser utilizado para formatar o banco de dados em caso de erros.

Drop database daylitask; -- Deleta o banco de dados existente.

CREATE database daylitask; -- Cria novamente o banco de dados.

use daylitask; -- faz os demais metodos utilizarem este banco de dados.

-- Cria a tabela de tarefas.
create table Tarefas( 
	id INT AUTO_INCREMENT PRIMARY KEY,
    userid INT,
    titulo varchar(30),
    datat varchar(30),
    descricao LONGTEXT,
    statust varchar(30)
);

-- Cria a tabela do histórico.
create table Historico(
	id INT AUTO_INCREMENT PRIMARY KEY,
    userid INT,
    titulo varchar(30),
    datat varchar(10),
    descricao LONGTEXT,
    statust varchar(30)
);

-- Cria a tabela de usúarios.
create table Usuarios(
	id INT AUTO_INCREMENT PRIMARY KEY,
    login varchar(30),
    senha varchar(30)
);

use daylitask; -- faz os demais metodos utilizarem este banco de dados.

drop user 'Dayli'@'localhost'; -- Exclui o usuario que faz integração do app ao banco de dados.

CREATE USER 'Dayli'@'localhost' IDENTIFIED BY 'UserBank1234'; -- Cria o usuario que faz integração do app ao banco de dados.

GRANT INSERT, UPDATE, DELETE, SELECT, ALTER ON *.* TO 'Dayli'@'localhost'; -- Define as  permições do usuario.

FLUSH PRIVILEGES; -- Atualiza a atribuição de permições.

-- Cria duas tarefas base para verificar o funcionamento do banco de dados.
INSERT INTO tarefas (userid, titulo, datat, descricao, statust) VALUES (1, "Tarefa 1", "11/07/2005", "Descrição foda 1", "Ativa");
INSERT INTO historico (userid, titulo, datat, descricao, statust) VALUES (1, "Tarefa 2", "11/07/2025", "Descrição foda 2", "Finalizada");
INSERT INTO usuarios (login, senha) VALUES ("Master", "1");