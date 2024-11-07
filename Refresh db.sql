-- Pode ser utilizado para formatar o banco de dados em caso de erros.

Drop database daylitask; -- Deleta o banco de dados existente.

CREATE database daylitask; -- Cria novamente o banco de dados.

use daylitask; -- faz os demais metodos utilizarem este banco de dados.

-- Cria a tabela de tarefas.
create table Tarefas( 
	id INT AUTO_INCREMENT PRIMARY KEY,
    titulo varchar(30),
    data varchar(10),
    descricao LONGTEXT,
    status varchar(30)
);

-- Cria a tabela do histórico.
create table Historico(
	id INT AUTO_INCREMENT PRIMARY KEY,
    titulo varchar(30),
    data varchar(10),
    descricao LONGTEXT,
    status varchar(30)
);

use daylitask; -- faz os demais metodos utilizarem este banco de dados.

drop user 'Dayli'@'localhost'; -- Exclui o usuario que faz integração do app ao banco de dados.

CREATE USER 'Dayli'@'localhost' IDENTIFIED BY '12983476'; -- Cria o usuario que faz integração do app ao banco de dados.

GRANT INSERT, UPDATE, DELETE, SELECT ON *.* TO 'Dayli'@'localhost'; -- Define as  permições do usuario.

FLUSH PRIVILEGES; -- Atualiza a atribuição de permições.

-- Cria duas tarefas base para verificar o funcionamento do banco de dados.
INSERT INTO tarefas (titulo, data, descricao, status) VALUES ("Tarefa 1", "11/07/2005", "Descrição foda 1", "Ativa");
INSERT INTO historico (titulo, data, descricao, status) VALUES ("Tarefa 2", "11/07/2025", "Descrição foda 2", "Finalizada");