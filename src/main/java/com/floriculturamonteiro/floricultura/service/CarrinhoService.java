package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.checkout.Checkout;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.Links;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.LinkRelation;
import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import com.floriculturamonteiro.floricultura.model.produto.Flores;
import com.floriculturamonteiro.floricultura.model.pedido.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CarrinhoService {
    //atributos
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final AdminService adminService;
    private final EmailService emailService;
    private final RegioesAtendidasService regioesAtendidasService;
    private final PagBankCheckoutService pagBankCheckoutService;

    //criar um carrinho
    public Carrinho criarCarrinho() {
        Carrinho carrinho = new Carrinho();
        return carrinhoRepository.save(carrinho);
    }

    //adicionar itens ao carrinho
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

    @Transactional
    public String finalizarCompra(Long carrinhoId, Cliente cliente) throws MessagingException {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // 1. calcula o subtotal dos itens (metodo na classe Carrinho)
       BigDecimal subtotal = carrinho.calcularSubTotalItens();

       // 2. calcula o valor do cartão de mensagem (caso seja marcada a opção)
       BigDecimal valorCartao = carrinho.isIncluirCartaoMensagem()
               ? new BigDecimal("10.00")
               : BigDecimal.ZERO;

       // 3. obtém o valor da entrega
       String regiao = cliente.getEndereco().getRegiao();
       BigDecimal valorEntrega = regioesAtendidasService.getPrecoEntrega(regiao);

       // 4. calcular o total final
       BigDecimal totalFinal = subtotal.add(valorCartao).add(valorEntrega);

       // 5. atualiza os valores no carrinho
       carrinho.setSubTotalItens(subtotal);
       carrinho.setValorCartaoMensagem(valorCartao);
       carrinho.setValorEntrega(valorEntrega);
       carrinho.setTotalFinal(totalFinal);
       carrinho.setCliente(cliente);
       carrinho.setDataHoraCompra(LocalDateTime.now());

       Checkout response = pagBankCheckoutService.criarCheckout(carrinho);
       String paymentUrl = response.getLinks().stream().filter(link -> LinkRelation.PAY.equals(link.getRel()))
                       .findFirst().map(Links::getHref).orElseThrow(() -> new RuntimeException("Link nao encontrado"));
       carrinho.setFinalizado(true);
        carrinhoRepository.save(carrinho);

        //enviar o email após finalização da compra
        Context context = new Context();
        context.setVariable("clienteNome", cliente.getNome());
        context.setVariable("statusCarrinho", "compra finalizada");
        context.setVariable("totalCarrinho", totalFinal);
        context.setVariable("itens", carrinho.getItens());
        context.setVariable("incluirCartaoMensagem", carrinho.isIncluirCartaoMensagem());

        emailService.enviarEmail(cliente.getEmail(), "status do seu pedido", "email", context);

        //limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());

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
