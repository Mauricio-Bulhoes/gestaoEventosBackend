-- Migration para adicionar campos de auditoria na tabela eventos (específico para H2)

ALTER TABLE eventos 
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE eventos 
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Cria índice para melhorar performance em consultas por data de criação
CREATE INDEX idx_eventos_created_at ON eventos(created_at);

-- Cria índice para melhorar performance em consultas por data de atualização
CREATE INDEX idx_eventos_updated_at ON eventos(updated_at);