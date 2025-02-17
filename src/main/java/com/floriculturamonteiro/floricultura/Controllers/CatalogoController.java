package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.ArmazenamentoImgService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoController {
    //atributos
    @Autowired
    private AdminService adminService;
    @Autowired
    private CarrinhoService carrinhoService;


    //página de catálogo
    @GetMapping
    public String catalogo(Model model){
        Carrinho carrinho = carrinhoService.criarCarrinho();
        model.addAttribute("carrinhoId", carrinho.getId());
        model.addAttribute("flores", adminService.buscarFloresEmEstoque());
        carrinhoService.limparCarrinhosVazios();
        return "catalogo";
    }
}
