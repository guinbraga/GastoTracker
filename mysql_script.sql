CREATE DATABASE gerenciamento_gastos;

USE gerenciamento_gastos;

CREATE TABLE gastos(
	id BIGINT auto_increment PRIMARY KEY,
    valor DECIMAL(10, 2) NOT NULL,
    categoria varchar(50) NOT NULL,
	data_gasto timestamp default current_timestamp  NOT NULL,
    forma_pagamento varchar(50) NOT NULL default 'Cartão',
    descricao varchar(100)
);

