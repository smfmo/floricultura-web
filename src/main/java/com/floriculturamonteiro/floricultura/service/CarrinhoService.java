package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.repositories.ItemCarrinhoRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarrinhoService {
    //atributos
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final AdminService adminService;
    private final EmailService emailService;
    private final RegioesAtendidasService regioesAtendidasService;

    @Autowired
    public CarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository,
                           CarrinhoRepository carrinhoRepository,
                           AdminService adminService,
                           EmailService emailService,
                           RegioesAtendidasService regioesAtendidasService) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.adminService = adminService;
        this.emailService = emailService;
        this.regioesAtendidasService = regioesAtendidasService;
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
        itemCarrinhoRepository.save(item);
    }

    public List<ItemCarrinho> ListarItensCarrinho(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }

    @Transactional
    public void finalizarCompra(Long carrinhoId, Cliente cliente) throws MessagingException {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // 1. calcula o subtotal dos itens (metodo na calsse Carrinho)
       BigDecimal subtotal = carrinho.calcularSubTotalItens();

       // 2. calcula o valor do cartão de mensagem (caso seja marcada a opção)
       BigDecimal valorCartao = cliente.isIncluirCartaoMensagem()
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

        carrinhoRepository.save(carrinho);

        //enviar o email após finalização da compra
        Context context = new Context();
        context.setVariable("clienteNome", cliente.getNome());
        context.setVariable("statusCarrinho", "compra finalizada");
        context.setVariable("totalCarrinho", totalFinal);
        context.setVariable("itens", carrinho.getItens());
        context.setVariable("incluirCartaoMensagem", cliente.isIncluirCartaoMensagem());

        emailService.enviarEmail(cliente.getEmail(), "status do seu pedido", "email", context);

        //limpar o carrinho após a compra
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
    }

    //metodo para exibir as compras no controle de vendas
    public List<Carrinho> exibirCompras() {
        return carrinhoRepository.EncontrarTodosComItens();
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
