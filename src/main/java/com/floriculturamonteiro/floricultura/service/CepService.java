package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    private static final List<String> CIDADES_ATENDIDAS = List.of("Novo Gama", "Valparaíso de Goiás");

    //metodo de buscar o endereco pelo cep
    public Endereco buscarEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIA_CEP_URL, cep);
        return restTemplate.getForObject(url, Endereco.class);
    }

    //Validação das regiões atendidas
    public boolean cepAtendido(String cep) {
        Endereco endereco = buscarEnderecoPeloCep(cep);
        if (endereco == null) {
            return false; //CEP inválido
        }
        return CIDADES_ATENDIDAS.contains(endereco.getLocalidade());
    }
}
