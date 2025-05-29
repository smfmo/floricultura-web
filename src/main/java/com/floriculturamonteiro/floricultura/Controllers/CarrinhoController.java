package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.Controllers.dtos.ClienteDto;
import com.floriculturamonteiro.floricultura.Controllers.mappers.ClienteMapper;
import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.pedido.ItemCarrinho;
import com.floriculturamonteiro.floricultura.model.produto.Flores;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import com.floriculturamonteiro.floricultura.service.ClienteService;
import com.floriculturamonteiro.floricultura.service.ManutencaoCarrinhoService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
@Scope("session") //um carrinho por sessão de usuário
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;
    private final AdminService adminService;
    private final CarrinhoRepository carrinhoRepository;
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final ManutencaoCarrinhoService manutencaoCarrinhoService;


    @GetMapping("") //vizualizar o carrinho
    public String verCarrinho(HttpSession session, Model model) {
        Long carrinhoId = (Long) session.getAttribute("carrinhoId");

        if (carrinhoId == null) {
            return "redirect:/catalogo"; //se não houver carrinho na sessão retorna para o catálogo
        }

        List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
        BigDecimal total = itens.stream() //calcular o total do carrinho
               .map(ItemCarrinho::getPrecoTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);
        model.addAttribute("carrinhoId", carrinhoId);

        return "carrinho";
    }

    @GetMapping("/adicionarItem")//adicionar itens ao carrinho
    public String adicionarItem(@RequestParam Long floresId,
                                @RequestParam int quantidade,
                                HttpSession session) {
        carrinhoService.limparCarrinhosVazios(); //limpar carrinhos vazios

        Long carrinhoId = (Long) session.getAttribute("carrinhoId");
        if (carrinhoId == null || !carrinhoRepository.existsById(carrinhoId)) {
            Carrinho novoCarrinho = carrinhoService.criarCarrinho();//se não houver carrinho na sessão, cria um novo
            carrinhoId = novoCarrinho.getId();
            session.setAttribute("carrinhoId", carrinhoId);
        }

        Flores flores = adminService.buscarPeloId(floresId).orElseThrow();//adicionar item ao carrinho

        BigDecimal precoTotal = flores.getPreco().multiply(BigDecimal.valueOf(quantidade));

        carrinhoService.adicionarFloresAoCarrinho(carrinhoId, floresId, quantidade, flores.getNome(), precoTotal);

        return "redirect:/carrinho";
    }

    @DeleteMapping("/remover/{itemId}")
    public ResponseEntity<Void> removerItemDoCarrinho(@PathVariable Long itemId) {
        carrinhoService.excluirItensDoCarrinho(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/finalizar")
    public String mostrarFormCliente(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long carrinhoId = (Long) session.getAttribute("carrinhoId");

        model.addAttribute("carrinhoId", carrinhoId);
        model.addAttribute("cliente", new Cliente());
        List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
        model.addAttribute("itens", itens);


        if (carrinhoId == null || !carrinhoRepository.existsById(carrinhoId)) {
            redirectAttributes.addAttribute("carrinhoExpirado", true);
            return "redirect:/catalogo";
        }
        if (itens == null || itens.isEmpty()) {
            redirectAttributes.addAttribute("carrinhoVazio", true);
            return "redirect:/catalogo";
        }

        BigDecimal total = itens.stream()
                .map(ItemCarrinho::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Boolean incluirCartaoMensagem = (Boolean) session.getAttribute("incluirCartaoMensagem");
        if (incluirCartaoMensagem == null) {
            incluirCartaoMensagem = false; // Valor padrão
        }

        model.addAttribute("incluirCartaoMensagem", incluirCartaoMensagem);
        model.addAttribute("total", total);


        return "formulario-cliente";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(
            @ModelAttribute("cliente") @Valid ClienteDto clienteDto,
            BindingResult bindingResult,
            @RequestParam(required = false) String cartaoMensagemDestinatario, //cartão opcional
            @RequestParam(required = false) boolean incluirCartaoMensagem,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        Long carrinhoId = (Long) session.getAttribute("carrinhoId");

        if (carrinhoId == null || !carrinhoRepository.existsById(carrinhoId)) {
            redirectAttributes.addAttribute("carrinhoExpirado", true);
            return "redirect:/catalogo";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("carrinhoId", carrinhoId);
            List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
            model.addAttribute("itens", itens);
            model.addAttribute("total", itens.stream()
                    .map(ItemCarrinho::getPrecoTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            model.addAttribute("incluirCartaoMensagem", incluirCartaoMensagem);
            return "formulario-cliente"; // Retorna para a view com os erros
        }


        session.setAttribute("incluirCartaoMensagem", incluirCartaoMensagem);
        Carrinho carrinho = carrinhoService.buscarCarrinhoPorId(carrinhoId)
                        .orElseThrow(() -> new RuntimeException("carrinho não encontrado"));

        carrinho.setCartaoMensagemDestinatario(cartaoMensagemDestinatario);
        carrinho.setIncluirCartaoMensagem(incluirCartaoMensagem);

        var clienteEntidade = clienteMapper.toEntity(clienteDto);
        clienteService.salvarCliente(clienteEntidade); //salva o cliente no banco de dados

        try{
            String pagBankUrl = carrinhoService.finalizarCompra(carrinhoId, clienteEntidade);
            session.removeAttribute("carrinhoId"); //aqui remove o carrinho da sessão após finalizar a compra
            carrinhoService.limparCarrinhosVazios();
            redirectAttributes.addFlashAttribute("sucess", "Compra finalizada com sucesso!");
            return "redirect:" + pagBankUrl;
        } catch (MessagingException e){ //erro ao envial email
            redirectAttributes.addFlashAttribute("error",
                    "Compra finalizada, mas ocorreu um erro ao enviar o email de confirmação!");
            redirectAttributes.addAttribute("carrinhoExpirado", true);
            redirectAttributes.addAttribute("error", "Erro ao processar pagamento");
            return "redirect:/catalogo";
        }
    }
    @GetMapping("/confirmacao")
    public String confirmacaoCompra(){
        return "confirmacao-compra";
    }
}
