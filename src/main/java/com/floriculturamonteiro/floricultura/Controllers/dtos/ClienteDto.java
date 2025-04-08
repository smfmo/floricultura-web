package com.floriculturamonteiro.floricultura.Controllers.dtos;

public record ClienteDto(
        String nome,
        String telefone,
        String email,
        EnderecoDto endereco) {
}
