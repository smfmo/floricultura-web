package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private AdminService adminService;

    //criar um carrinho
    public Carrinho criarCarrinho() {
        Carrinho carrinho = new Carrinho();
        return carrinhoRepository.save(carrinho);
    }

    //adicionar itens ao carrinho
    public void adicionarFloresAoCarrinho(Long carrinhoId, Long floresId,
                                        int quantidade, String nomeProduto, double precoTotal) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        Flores flores = adminService.buscarPeloId(floresId).orElseThrow();

        ItemCarrinho item = new ItemCarrinho();
        item.setPrecoTotal(flores.getPreco() * quantidade);
        item.setNomeProduto(flores.getNome());
        item.setFlores(flores);
        item.setQuantidade(quantidade);
        item.setCarrinho(carrinho);
        itemCarrinhoRepository.save(item);
    }

    public List<ItemCarrinho> ListarItensCarrinho(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }

    public void finalizarCompra(Long carrinhoId, Cliente cliente) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        carrinho.setCliente(cliente);
        carrinhoRepository.save(carrinho);
        carrinho.setDataHoraCompra(LocalDateTime.now());
        //aqui vai vir a lógica de finalização de compra, salvar no banco de dados(vai aqui)


        //limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
    }

    //metodo para exibir as compras no controle de vendas
    public List<Carrinho> exibirCompras() {
        return carrinhoRepository.findAllWithItens();
    }

    //limpar carrinhos vazios
    public void limparCarrinhosVazios(){
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        for (Carrinho carrinho : carrinhos) {
            if (carrinho.getItens().isEmpty()) {
                carrinhoRepository.delete(carrinho);
            }
        }
    }
}
