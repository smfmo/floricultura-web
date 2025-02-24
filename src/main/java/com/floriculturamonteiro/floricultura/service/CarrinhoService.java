package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarrinhoService {
    //atributos
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final AdminService adminService;

    @Autowired
    public CarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository,
                           CarrinhoRepository carrinhoRepository,
                           AdminService adminService) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.adminService = adminService;
    }

    //criar um carrinho
    public Carrinho criarCarrinho() {
        Carrinho carrinho = new Carrinho();
        return carrinhoRepository.save(carrinho);
    }

    //adicionar itens ao carrinho
    public void adicionarFloresAoCarrinho(Long carrinhoId,
                                          Long floresId,
                                          int quantidade,
                                          String nomeProduto,
                                          BigDecimal precoTotal) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        Flores flores = adminService.buscarPeloId(floresId).orElseThrow();

        ItemCarrinho item = new ItemCarrinho();
        item.setPrecoTotal(flores.getPreco().multiply(BigDecimal.valueOf(quantidade)));
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

        BigDecimal totalCarrinho = carrinho.getItens().stream()
                .map(ItemCarrinho::getPrecoTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrinho.setTotalCarrinho(totalCarrinho);


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

    public void carrinhoConcluido(Long carrinhoId){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
        carrinho.setConcluido(true);
        carrinhoRepository.save(carrinho);
    }

    public void limparCarrinhosConcluidos(){
        List<Carrinho> carrinhosConcluidos = carrinhoRepository.findByConcluido(true);

        //remover os itens de cada cada carrinho concluído
        for(Carrinho carrinho : carrinhosConcluidos){
            itemCarrinhoRepository.deleteAll(carrinho.getItens());
        }

        //remove os carrihos concluidos
        carrinhoRepository.deleteAll(carrinhosConcluidos);
    }

}
