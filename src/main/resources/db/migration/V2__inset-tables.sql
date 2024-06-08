-- Insert clentes
INSERT INTO cliente (nome, email, telefone) VALUES
('João Silva', 'joao.silva@example.com', '11987654321'),
('Maria Oliveira', 'maria.oliveira@example.com', '11987654322'),
('Carlos Pereira', 'carlos.pereira@example.com', '11987654323');

-- Insert produtos
INSERT INTO produto (categoria, valor, descricao) VALUES
('Eletrônicos', 2000.00, 'Smartphone X'),
('Livros', 30.00, 'Aprenda Java em 21 dias'),
('Eletrônicos', 300.00, 'Fone de Ouvido Bluetooth');

-- Insert vendas
INSERT INTO venda (observacoes, cliente_id, data_venda, total) VALUES
('Primeira compra', 1, NOW(), 2300.00), -- Total atualizado para refletir a soma dos valores dos itens
('Compra de livros', 2, NOW(), 30.00),
('Aparelho eletrônico', 3, NOW(), 300.00);

-- Insert items da venda
-- Assumindo que os IDs das vendas e produtos são gerados sequencialmente conforme as inserções anteriores
INSERT INTO item_venda (venda_id, produto_id, quantidade, valor_unitario, valor_total) VALUES
(1, 1, 1, 2000.00, 2000.00),
(1, 3, 1, 300.00, 300.00),
(2, 2, 1, 30.00, 30.00),
(3, 3, 1, 300.00, 300.00);