CREATE TABLE flores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) ,
    url_imagem VARCHAR(255) ,
    preco NUMERIC(10, 2) ,
    descricao TEXT ,
    cuidados TEXT ,
    cor VARCHAR(100) ,
    disponibilidade VARCHAR(100) ,
    embalagem VARCHAR(100)
);