package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControleVendaService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;

    //exibir as compras no controle de vendas
    public List<Carrinho> exibirCompras() {
        return carrinhoRepository.EncontrarTodosComItens();
    }

    public List<Carrinho> pesquisarCliente(String nome) {
        if (nome == null || nome.isBlank()) {
            return exibirCompras();
        }

        return carrinhoRepository.findByNomeClienteIgnoreCase(nome);
    } //buscar carrinho pelo nome do cliente no controle de vendas

    public List<Carrinho> pesquisaByExample(String nome){
        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Cliente> clientes = clienteRepository.findAll(Example.of(cliente, matcher));

        return carrinhoRepository.findByClienteIn(clientes);

    } //abordagem de pesquisa alternativa utilizando a interface QueryByExample
}
