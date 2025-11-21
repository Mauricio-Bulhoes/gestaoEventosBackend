-- Script específico para PostgreSQL
CREATE SEQUENCE eventos_id_seq;

CREATE TABLE eventos (
  id BIGINT NOT NULL DEFAULT nextval('eventos_id_seq'),
  descricao VARCHAR(1000),
  local VARCHAR(200),
  titulo VARCHAR(100) NOT NULL,
  data_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  deleted BOOLEAN NOT NULL DEFAULT false,
  CONSTRAINT eventos_pkey PRIMARY KEY (id)
);

ALTER SEQUENCE eventos_id_seq OWNED BY eventos.id;

-- Index para melhorar performance em consultas por data
CREATE INDEX idx_eventos_data_hora ON eventos(data_hora);

-- Index para soft delete
CREATE INDEX idx_eventos_deleted ON eventos(deleted) WHERE deleted = false;