package com.floriculturamonteiro.floricultura.dtos;

public record FloresDto( String nome,
                         String urlImagem,
                         Double preco,
         String descricao,
         String cuidados, String cor,
         String disponibilidade,
         String embalagem
                         ) {

}
