package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private AdminService adminService;

    //vizualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public String verCarrinho(@PathVariable Long carrinhoId, Model model) {
        List<ItemCarrinho> itens = carrinhoService.ListarItensCarrinho(carrinhoId);
        //calcular o total do carrinho
        double total = itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getFlores()
                        .getPreco()).sum();
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
                                          @RequestParam double precoTotal,
                                          HttpSession session) {
        carrinhoService.adicionarFloresAoCarrinho(carrinhoId, floresId,
                quantidade, nomeProduto, precoTotal);
        session.setAttribute("carrinhoId", carrinhoId); //aqui armazena o ID do carrinho na sessão
        return "redirect:/carrinho/" + carrinhoId;
    }

    @PostMapping("/finalizar/{carrinhoId}")
    public String finalizarCompra(@PathVariable Long carrinhoId, HttpSession session) {
        carrinhoService.finalizarCompra(carrinhoId);
        session.removeAttribute("carrinhoId"); //aqui remove o carrinho da sessão após finalizar a compra
        return "redirect:/";
    }

    //metodo para adicionar itens ao carrinho existente ou criar um novo
    @GetMapping("/adicionarItem")
    public String adicionarItem(@RequestParam Long floresId,
                                @RequestParam int quantidade,
                                HttpSession session) {
        Long carrinhoId = (Long) session.getAttribute("carrinhoId");
        if (carrinhoId == null) {
            //se não houver carrinho na sessão, cria um novo
            carrinhoId = carrinhoService.criarCarrinho().getId();
            session.setAttribute("carrinhoId", carrinhoId);
        }

        //adicionar item ao carrinho
        Flores flores = adminService.buscarPeloId(floresId).orElseThrow();
        carrinhoService.adicionarFloresAoCarrinho(carrinhoId, floresId, quantidade, flores.getNome(),
                flores.getPreco() * quantidade);

        return "redirect:/carrinho/" + carrinhoId;
    }
}
