package com.floriculturamonteiro.floricultura.Controllers.dtos;

import com.floriculturamonteiro.floricultura.model.Endereco;

public record EnderecoDto(
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String numero,
        String regiao) {

    public Endereco mapearParaEndereco(){
        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);
        endereco.setNumero(numero);
        endereco.setRegiao(regiao);

        return endereco;
    }
}
