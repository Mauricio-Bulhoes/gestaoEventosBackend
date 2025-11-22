-- Migration para adicionar campos de auditoria na tabela eventos (específico para postgreSQL)

ALTER TABLE eventos 
ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE eventos 
ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Cria índice para melhorar performance em consultas por data de criação
CREATE INDEX idx_eventos_created_at ON eventos(created_at);

-- Cria índice para melhorar performance em consultas por data de atualização
CREATE INDEX idx_eventos_updated_at ON eventos(updated_at);

-- Cria trigger para atualizar automaticamente o campo updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_eventos_updated_at
    BEFORE UPDATE ON eventos
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Comentários para documentação
COMMENT ON COLUMN eventos.created_at IS 'Data e hora de criação do registro';
COMMENT ON COLUMN eventos.updated_at IS 'Data e hora da última atualização do registro';