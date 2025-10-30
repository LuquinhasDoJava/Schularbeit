CREATE  DATABASE frota
USE frota

-- Inserir 5 Marcas
INSERT INTO marca (nome, pais) VALUES
('Volvo', 'Suécia'),
('Mercedes-Benz', 'Alemanha'),
('Scania', 'Suécia'),
('Ford', 'EUA'),
('Iveco', 'Itália');

-- Inserir 15 Caminhões
INSERT INTO caminhao (modelo, marca_id, placa, carga_maxima, ano, altura, comprimento, largura, volume, fator_cubagem) VALUES
-- Volvo
('FH 540', 1, 'VOL1234', 25000.0, 2023, 3.0, 15.0, 2.6, 117.0, 300.0),
('FM 420', 1, 'VOL5678', 20000.0, 2022, 2.8, 14.0, 2.5, 98.0, 300.0),
('VM 370', 1, 'VOL9012', 18000.0, 2021, 2.7, 13.0, 2.4, 84.24, 300.0),

-- Mercedes-Benz
('Actros 2545', 2, 'MER3456', 27000.0, 2023, 3.1, 16.0, 2.6, 128.96, 300.0),
('Axor 3340', 2, 'MER7890', 22000.0, 2022, 2.9, 14.5, 2.5, 105.125, 300.0),
('Atego 2425', 2, 'MER1234', 15000.0, 2021, 2.6, 12.0, 2.4, 74.88, 300.0),

-- Scania
('R 450', 3, 'SCA5678', 26000.0, 2023, 3.0, 15.5, 2.6, 120.9, 300.0),
('G 410', 3, 'SCA9012', 21000.0, 2022, 2.8, 14.2, 2.5, 99.4, 300.0),
('P 360', 3, 'SCA3456', 17000.0, 2021, 2.7, 13.5, 2.4, 87.48, 300.0),

-- Ford
('Cargo 2429', 4, 'FOR7890', 16000.0, 2023, 2.8, 12.0, 2.4, 80.64, 300.0),
('F-4000', 4, 'FOR1234', 14000.0, 2022, 2.6, 11.5, 2.3, 68.77, 300.0),
('F-350', 4, 'FOR5678', 12000.0, 2021, 2.5, 11.0, 2.3, 63.25, 300.0),

-- Iveco
('Stralis XP', 5, 'IVE9012', 24000.0, 2023, 2.9, 15.0, 2.5, 108.75, 300.0),
('Trakker AD', 5, 'IVE3456', 19000.0, 2022, 2.8, 13.8, 2.4, 92.736, 300.0),
('Daily 70C', 5, 'IVE7890', 8000.0, 2021, 2.4, 9.0, 2.2, 47.52, 300.0);

-- Inserir 6 Caixas
INSERT INTO caixa (altura, largura, comprimento, material, limite_peso) VALUES
-- Caixas pequenas
(0.3, 0.4, 0.5, 'Papelão', 10.0),
(0.4, 0.5, 0.6, 'Papelão', 15.0),
(0.5, 0.6, 0.7, 'Papelão', 20.0),

-- Caixas médias
(0.6, 0.7, 0.8, 'Madeira', 30.0),
(0.7, 0.8, 0.9, 'Madeira', 40.0),

-- Caixa grande
(0.8, 0.9, 1.0, 'Plástico', 50.0);

-- Inserir 12 Produtos
INSERT INTO produto (peso, altura, largura, comprimento) VALUES
-- Produtos leves e pequenos
(2.5, 0.2, 0.3, 0.4),   -- Livros
(1.8, 0.15, 0.25, 0.35), -- Roupas
(3.2, 0.25, 0.35, 0.45), -- Eletrônicos pequenos

-- Produtos médios
(8.5, 0.4, 0.5, 0.6),   -- Peças automotivas
(12.3, 0.45, 0.55, 0.65), -- Ferramentas
(15.7, 0.5, 0.6, 0.7),  -- Equipamentos

-- Produtos pesados
(25.4, 0.6, 0.7, 0.8),  -- Máquinas pequenas
(32.1, 0.65, 0.75, 0.85), -- Motores
(28.9, 0.7, 0.8, 0.9),  -- Geradores

-- Produtos diversos
(5.5, 0.3, 0.4, 0.5),   -- Alimentos
(7.8, 0.35, 0.45, 0.55), -- Bebidas
(18.6, 0.55, 0.65, 0.75); -- Materiais de construção

-- Inserir 12 Encomendas com TODOS os campos obrigatórios
INSERT INTO encomenda (encomenda_id, caixa_id, caminhao_id, produto_id, peso_real, distancia_km) VALUES
-- Encomenda 1: Caixa pequena (0.3x0.4x0.5 = 0.06m³ * 300 = 18kg cubado)
(1, 1, 1, 1, 2.5, 150.0),

-- Encomenda 2: Caixa média (0.6x0.7x0.8 = 0.336m³ * 300 = 100.8kg cubado)
(2, 4, 2, 4, 8.5, 200.0),

-- Encomenda 3: Caixa grande (0.8x0.9x1.0 = 0.72m³ * 300 = 216kg cubado)
(3, 6, 3, 7, 25.4, 300.0),

-- Encomenda 4: Caixa pequena (0.4x0.5x0.6 = 0.12m³ * 300 = 36kg cubado)
(4, 2, 4, 2, 1.8, 180.0),

-- Encomenda 5: Caixa pequena (0.5x0.6x0.7 = 0.21m³ * 300 = 63kg cubado)
(5, 3, 5, 3, 3.2, 220.0),

-- Encomenda 6: Caixa média (0.7x0.8x0.9 = 0.504m³ * 300 = 151.2kg cubado)
(6, 5, 6, 5, 12.3, 250.0),

-- Encomenda 7: Caixa pequena (peso real maior que cubado)
(7, 1, 7, 6, 15.7, 170.0),

-- Encomenda 8: Caixa média (peso cubado maior que real)
(8, 4, 8, 8, 32.1, 280.0),

-- Encomenda 9: Caixa pequena (peso cubado maior que real)
(9, 2, 9, 9, 28.9, 190.0),

-- Encomenda 10: Caixa pequena (peso cubado maior que real)
(10, 3, 10, 10, 5.5, 160.0),

-- Encomenda 11: Caixa média (peso cubado maior que real)
(11, 5, 11, 11, 7.8, 210.0),

-- Encomenda 12: Caixa grande (peso cubado maior que real)
(12, 6, 12, 12, 18.6, 320.0);

SELECT *
FROM caixa;

SELECT *
FROM caminhao;

SELECT *
FROM encomenda;

SELECT *
FROM marca;

SELECT *
FROM produto