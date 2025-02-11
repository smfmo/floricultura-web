package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void finalizarCompra(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        //aqui vai vir a lógica de finalização de compra, salvar no banco de dados(vai aqui)


        //limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
    }
}
