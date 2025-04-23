package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import com.floriculturamonteiro.floricultura.service.ControleVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ControleVendaController {

    private final ControleVendaService controleVendaService;
    private final CarrinhoService carrinhoService;

    //ver as vendas no controle de Vendas
    @GetMapping("controleVenda")
    public String exibirCompras(Model model,
                                @RequestParam(name = "nome",
                                        required = false) String nome){

        if (nome != null && !nome.isBlank()) {
            List<Carrinho> carrinhos = controleVendaService.pesquisaByExample(nome);
            model.addAttribute("carrinhos", carrinhos);
            model.addAttribute("termoPesquisa", nome);
        } else {
            List<Carrinho> carrinhos = controleVendaService.exibirCompras();
            model.addAttribute("carrinhos", carrinhos);
        }

        return "controleVenda";
    }

    //marcar os carrinhos concluidos
    @PostMapping("/controleVenda/concluir/{carrinhoId}")
    public String marcarComoConcluido(@PathVariable Long carrinhoId){
        carrinhoService.carrinhoConcluido(carrinhoId);
        return "redirect:/controleVenda";
    }

    //limpar os carrinhos que ja foram concluídos
    @PostMapping("controleVenda/limpar")
    public String limparCarrinhosConcluidos(){
        carrinhoService.limparCarrinhosConcluidos();
        return "redirect:/controleVenda";
    }
}
