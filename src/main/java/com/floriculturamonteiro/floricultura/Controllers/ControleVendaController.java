package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import com.floriculturamonteiro.floricultura.service.ControleVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
                                        required = false) String nome,
                                @RequestParam(defaultValue = "0") Integer pagina,
                                @RequestParam(defaultValue = "10") Integer tamanhoPagina){


        if (nome != null && !nome.isBlank()) {
            Page<Carrinho> carrinhos = controleVendaService
                    .pesquisarCliente(nome, pagina, tamanhoPagina);
            model.addAttribute("carrinhos", carrinhos.getContent());
            model.addAttribute("termoPesquisa", nome);
            model.addAttribute("paginaAtual", pagina);
            model.addAttribute("totalPaginas", carrinhos.getTotalPages());
        } else {
            Page<Carrinho> carrinhos = controleVendaService.listarCarrinhos(pagina, tamanhoPagina);
            model.addAttribute("paginaAtual", pagina);
            model.addAttribute("carrinhos", carrinhos.getContent());
            model.addAttribute("totalPaginas", carrinhos.getTotalPages());
        }
        List<Integer> tamanhos = List.of(5, 10, 15);
        model.addAttribute("tamanhos", tamanhos);
        model.addAttribute("tamanhoPagina", tamanhoPagina);

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
