CREATE TABLE lancamento(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT;
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria BIGINT(20) NOT NULL,
	codigo_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) values ('Salário Mensal', '2017-06-10', null, 650,00, 'Distribuição de Lucros', 'RECEITA', 1, 1); 
