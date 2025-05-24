package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControleVendaService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;

    public Page<Carrinho> listarCarrinhos(Integer pagina, Integer tamanhoPagina){
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return carrinhoRepository.EncontrarTodosComItens(pageRequest);
    } //metodo de


    public Page<Carrinho> pesquisarCliente(String nome, Integer pagina, Integer tamanhoPagina) {
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return carrinhoRepository.findByNomeClienteIgnoreCase(nome, pageRequest);
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
