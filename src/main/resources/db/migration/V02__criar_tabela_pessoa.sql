CREATE TABLE pessoa(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL, 	
	logradouro VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(15),
	cidade VARCHAR(30),
	estado VARCHAR(2),
	ativo BOOLEAN NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa(nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
values('Admin', 'Rua quatorze', '24A', 'Casa', 'Centro', '14500000', 'Guará', 'SP', true);
