CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL
);

CREATE TABLE produto (
    id BIGSERIAL PRIMARY KEY,
    categoria VARCHAR(15) NOT NULL,
    valor NUMERIC(10,2) NOT NULL, -- Tipo numeric para valor
    descricao varchar(100)
);

CREATE TABLE venda (
    id BIGSERIAL PRIMARY KEY,
    observacoes varchar(100),
    cliente_id BIGINT,
    data_venda TIMESTAMP,
    total NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE item_venda (
    id BIGSERIAL PRIMARY KEY,
    venda_id BIGINT,
    produto_id BIGINT,
    quantidade INT NOT NULL,
    valor_unitario NUMERIC(10,2) NOT NULL, -- Tipo numeric para valor unitário
    valor_total NUMERIC(10,2) NOT NULL, -- Tipo numeric para valor total
    FOREIGN KEY (venda_id) REFERENCES venda(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);