-- Script específico para H2 Database
CREATE TABLE eventos (
  id BIGINT AUTO_INCREMENT NOT NULL,
  descricao VARCHAR(1000),
  local VARCHAR(200),
  titulo VARCHAR(100) NOT NULL,
  data_hora TIMESTAMP NOT NULL,
  deleted BOOLEAN NOT NULL DEFAULT false,
  CONSTRAINT eventos_pkey PRIMARY KEY (id)
);

-- Index para melhorar performance em consultas por data
CREATE INDEX idx_eventos_data_hora ON eventos(data_hora);

-- Index para soft delete
CREATE INDEX idx_eventos_deleted ON eventos(deleted);