-- ========================================================
-- Script de Inicialização de Tabelas - Supabase (PostgreSQL)
-- Projeto: Gerenciador de Estoque
-- ========================================================

-- Limpeza preventiva (se necessário recriar)
-- DROP TABLE IF EXISTS movimentacoes CASCADE;
-- DROP TABLE IF EXISTS produtos CASCADE;
-- DROP TABLE IF EXISTS usuarios CASCADE;

-- 1. Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'OPERADOR',
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabela de Produtos (campos herdados e expandidos da Tela_principal.java)
CREATE TABLE IF NOT EXISTS produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    preco NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabela de Movimentações (Entrada e Saída de Estoque)
CREATE TABLE IF NOT EXISTS movimentacoes (
    id BIGSERIAL PRIMARY KEY,
    produto_id BIGINT NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    motivo VARCHAR(255),
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    data_movimentacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Índices para otimização de consultas e relatórios
CREATE INDEX IF NOT EXISTS idx_produtos_nome ON produtos(nome);
CREATE INDEX IF NOT EXISTS idx_movimentacoes_produto ON movimentacoes(produto_id);
CREATE INDEX IF NOT EXISTS idx_movimentacoes_data ON movimentacoes(data_movimentacao);

-- Trigger opcional para atualizar automaticamente o campo atualizado_em de produtos
CREATE OR REPLACE FUNCTION atualiza_data_modificacao()
RETURNS TRIGGER AS $$
BEGIN
    NEW.atualizado_em = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_produtos_atualizado_em ON produtos;
CREATE TRIGGER trg_produtos_atualizado_em
BEFORE UPDATE ON produtos
FOR EACH ROW
EXECUTE FUNCTION atualiza_data_modificacao();

-- Dados Iniciais de Demonstração (Seeds)
INSERT INTO usuarios (nome, email, senha, role) 
VALUES ('Administrador', 'admin@estoque.com', 'admin123', 'ADMIN')
ON CONFLICT (email) DO NOTHING;

INSERT INTO produtos (nome, descricao, preco, quantidade_estoque) VALUES
('Teclado Mecânico RGB', 'Teclado mecânico switch azul com iluminação RGB e layout ABNT2', 249.90, 15),
('Mouse Sem Fio 2.4GHz', 'Mouse ergonômico óptico com ajuste de DPI até 3200', 89.90, 30),
('Monitor 24 Pol Full HD', 'Monitor IPS 75Hz bordas ultrafinas com conexões HDMI e VGA', 680.00, 8),
('Headset Gamer 7.1', 'Headset com som surround virtual 7.1 e microfone antirruído', 199.90, 12)
ON CONFLICT DO NOTHING;

INSERT INTO movimentacoes (produto_id, tipo, quantidade, motivo) VALUES
(1, 'ENTRADA', 15, 'Estoque inicial'),
(2, 'ENTRADA', 30, 'Estoque inicial'),
(3, 'ENTRADA', 8, 'Estoque inicial'),
(4, 'ENTRADA', 12, 'Estoque inicial')
ON CONFLICT DO NOTHING;
