package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CatalogoController {
    //atributos
    private final AdminService adminService;
    private final CarrinhoService carrinhoService;

    //sobre nós
    @GetMapping("/sobre-nos")
    public String sobreNos(){
        return "sobre-nos";
    }

    //pagina inicial
    @GetMapping("/")
    public String PagInicial(){
        return "Pagina-inicial";
    }

    //contato e localização
    @GetMapping("/contato")
    public String contatoLoc(){
        return "contato";
    }

    //página de catálogo
    @GetMapping("/catalogo")
    public String catalogo(Model model){
        Carrinho carrinho = carrinhoService.criarCarrinho();
        model.addAttribute("carrinhoId", carrinho.getId());
        model.addAttribute("flores", adminService.buscarFloresEmEstoque());
        carrinhoService.limparCarrinhosVazios();
        return "catalogo";
    }
}
