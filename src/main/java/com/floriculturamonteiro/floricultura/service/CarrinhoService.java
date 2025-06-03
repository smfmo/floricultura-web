package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import com.floriculturamonteiro.floricultura.model.produto.Flores;
import com.floriculturamonteiro.floricultura.model.pedido.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrinhoService {
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final AdminService adminService;
    private final RegioesAtendidasService regioesAtendidasService;
    private final PagBankCheckoutService pagBankCheckoutService;

    public Carrinho criarCarrinho() {
        Carrinho carrinho = new Carrinho();
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public void adicionarFloresAoCarrinho(Long carrinhoId,
                                          Long floresId,
                                          int quantidade,
                                          String nomeProduto,
                                          BigDecimal precoTotal) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(()-> new RuntimeException("Carrinho nao encontrado com o id: " + carrinhoId));
        Flores flores = adminService.buscarPeloId(floresId)
                .orElseThrow(()-> new RuntimeException("Flores nao encontrado com o id: " + floresId));

        ItemCarrinho item = new ItemCarrinho();
        item.setPrecoTotal(flores.getPreco().multiply(BigDecimal.valueOf(quantidade)));
        item.setNomeProduto(flores.getNome());
        item.setFlores(flores);
        item.setQuantidade(quantidade);
        item.setValorUnitario(flores.getPreco());
        item.setCarrinho(carrinho);
        carrinho.setUltimaAtualizacao(LocalDateTime.now());
        itemCarrinhoRepository.save(item);
    }

    public List<ItemCarrinho> ListarItensCarrinho(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }

    public Optional<Carrinho> buscarCarrinhoPorId(Long carrinhoId) {
        return carrinhoRepository.findById(carrinhoId);
    }

    public Carrinho valorFinal(Long carrinhoId, Cliente cliente) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // 1. calcula subtotal dos itens.
        BigDecimal subtotal = carrinho.calcularSubTotalItens();

        // 2. Calcula o valor do cartão de mensagem (caso a opção seja marcada)
        BigDecimal valorCartao = carrinho.isIncluirCartaoMensagem()
                ? new BigDecimal("10.00")
                : BigDecimal.ZERO;

        // 3. obtém o valor da entrega
        String regiao = cliente.getEndereco().getRegiao();
        BigDecimal valorEntrega = regioesAtendidasService.getPrecoEntrega(regiao);

        // 4. calcula o total final
        BigDecimal totalFinal = subtotal.add(valorCartao).add(valorEntrega);

        //5. atualiza os valores no carrinho
        carrinho.setSubTotalItens(subtotal);
        carrinho.setValorCartaoMensagem(valorCartao);
        carrinho.setValorEntrega(valorEntrega);
        carrinho.setTotalFinal(totalFinal);
        carrinho.setCliente(cliente);
        carrinho.setDataHoraCompra(LocalDateTime.now());
        carrinho.setFinalizado(true);

        return carrinho;
    }

    @Transactional
    public String finalizarCompra(Long carrinhoId, Cliente cliente){
        Carrinho carrinho = valorFinal(carrinhoId, cliente);
       // 6. Redireciona o cliente para pagamento
       String paymentUrl = pagBankCheckoutService.responsePagBank(carrinho);
       carrinhoRepository.save(carrinho);

        itemCarrinhoRepository.deleteAll(carrinho.getItens());//limpar o carrinho após a compra

        return paymentUrl;
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

    public void excluirItensDoCarrinho(Long itemId){
        ItemCarrinho item = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encotrado"));

        itemCarrinhoRepository.delete(item);
    }

}
