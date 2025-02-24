package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.model.Endereco;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import com.floriculturamonteiro.floricultura.service.CepService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
@Scope("session") //um carrinho por sessão de usuário
public class CarrinhoController {

    private final CarrinhoService carrinhoService;
    private final AdminService adminService;
    private final CepService cepService;

    @Autowired
    public CarrinhoController(CarrinhoService carrinhoService,
                              AdminService adminService,
                              CepService cepService) {
        this.carrinhoService = carrinhoService;
        this.adminService = adminService;
        this.cepService = cepService;
    }

    //vizualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public String verCarrinho(@PathVariable Long carrinhoId, Model model) {
        List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
        //calcular o total do carrinho
       BigDecimal total = itens.stream()
               .map(ItemCarrinho::getPrecoTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);
        model.addAttribute("carrinhoId", carrinhoId);

        return "carrinho";
    }

    //adicionar os produtos ao carrinho
    @PostMapping("/adicionar")
    public String adicionarFlorAoCarrinho(@RequestParam Long carrinhoId,
                                          @RequestParam Long floresId,
                                          @RequestParam int quantidade,
                                          @RequestParam String nomeProduto,
                                          @RequestParam BigDecimal precoTotal,
                                          HttpSession session) {
        carrinhoService.adicionarFloresAoCarrinho(carrinhoId, floresId,
                quantidade, nomeProduto, precoTotal);
        session.setAttribute("carrinhoId", carrinhoId); //aqui armazena o ID do carrinho na sessão
        return "redirect:/carrinho/" + carrinhoId;
    }

    @GetMapping("/finalizar/{carrinhoId}")
    public String mostratFormCliente(@PathVariable Long carrinhoId, Model model) {
        model.addAttribute("carrinhoId", carrinhoId);
        model.addAttribute("cliente", new Cliente());
        List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
        model.addAttribute("itens", itens);

        BigDecimal total = itens.stream().map(ItemCarrinho::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("total", total);
        return "formulario-cliente";
    }
    @PostMapping("/finalizar/{carrinhoId}")
    public String finalizarCompra(@PathVariable Long carrinhoId,
                                  @RequestParam String nome,
                                  @RequestParam String telefone,
                                  @RequestParam String cep,
                                  @RequestParam String logradouro,
                                  @RequestParam String bairro,
                                  @RequestParam String localidade,
                                  @RequestParam String uf,
                                  @RequestParam String numero,
                                  @RequestParam String complemento,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        //validar o cep
        if (!cepService.cepAtendido(cep)){
            redirectAttributes.addFlashAttribute("error",
                    "Desculpe, não podemos prosseguir com a compra. Não atendemos sua região!");
            return "redirect:/carrinho/" + carrinhoId;
        }
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);

        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);

        cliente.setEndereco(endereco);

        carrinhoService.finalizarCompra(carrinhoId, cliente);
        session.removeAttribute("carrinhoId"); //aqui remove o carrinho da sessão após finalizar a compra
        carrinhoService.limparCarrinhosVazios();
        return "redirect:/catalogo";
    }

    //metodo para adicionar itens ao carrinho existente ou criar um novo
    @GetMapping("/adicionarItem")
    public String adicionarItem(@RequestParam Long floresId,
                                @RequestParam int quantidade,
                                HttpSession session) {
        //limpar carrinhos vazios
        carrinhoService.limparCarrinhosVazios();

        Long carrinhoId = (Long) session.getAttribute("carrinhoId");
        if (carrinhoId == null) {
            //se não houver carrinho na sessão, cria um novo
            carrinhoId = carrinhoService.criarCarrinho().getId();
            session.setAttribute("carrinhoId", carrinhoId);
        }

        //adicionar item ao carrinho
        Flores flores = adminService.buscarPeloId(floresId).orElseThrow();

        BigDecimal precoTotal = flores.getPreco().multiply(BigDecimal.valueOf(quantidade));

        carrinhoService.adicionarFloresAoCarrinho(carrinhoId, floresId, quantidade, flores.getNome(), precoTotal);

        return "redirect:/carrinho/" + carrinhoId;
    }
}
