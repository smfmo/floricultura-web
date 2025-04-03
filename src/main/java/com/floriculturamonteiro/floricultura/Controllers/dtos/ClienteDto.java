package com.floriculturamonteiro.floricultura.Controllers.dtos;

import com.floriculturamonteiro.floricultura.model.Cliente;

public record ClienteDto(
        String nome,
        String telefone,
        String email,
        EnderecoDto endereco) {

    public Cliente mapearParaCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setEndereco(endereco.mapearParaEndereco());

        return cliente;
    }

}
