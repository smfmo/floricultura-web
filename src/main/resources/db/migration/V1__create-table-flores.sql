CREATE TABLE flores (
    id SERIAL PRIMARY KEY,  -- ID gerado automaticamente
    nome VARCHAR(255) ,  -- Nome da flor (não pode ser nulo)
    url_imagem VARCHAR(255) ,  -- URL da imagem (não pode ser nulo)
    preco NUMERIC(10, 2) ,  -- Preço da flor (não pode ser nulo, com 2 casas decimais)
    descricao TEXT ,  -- Descrição da flor (não pode ser nulo)
    cuidados TEXT ,  -- Cuidados necessários (não pode ser nulo)
    cor VARCHAR(100) ,  -- Cor da flor (não pode ser nulo)
    disponibilidade VARCHAR(100) ,  -- Disponibilidade da flor (não pode ser nulo)
    embalagem VARCHAR(100)   -- Tipo de embalagem (não pode ser nulo)
);