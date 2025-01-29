CREATE TABLE flores (
    id SERIAL PRIMARY KEY,  -- ID gerado automaticamente
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    cuidados TEXT,
    valor BIGINT NOT NULL,
    disponibilidade TEXT,
    cor VARCHAR(100),
    embalagem VARCHAR(100)
);