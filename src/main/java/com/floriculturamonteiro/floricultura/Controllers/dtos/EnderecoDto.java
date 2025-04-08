package com.floriculturamonteiro.floricultura.Controllers.dtos;

public record EnderecoDto(
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String numero,
        String regiao) {
}
