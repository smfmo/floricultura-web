package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.ArmazenamentoImgService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public  class AdminController {

    private final AdminService adminService;
    private final ArmazenamentoImgService imgService;
    private final CarrinhoService carrinhoService;

    //Login do administrador
    @GetMapping("/loginAdm")
    public String loginAdm(){
        return "LoginAdm";
    }

    //página do administrador
    @GetMapping("/admin")
    public String admin(Model model){

        List<Flores> floresEmEstoque = adminService.buscarFloresEmEstoque();
        List<Flores> floresSemEstoque = adminService.buscarFloresSemEstoque();

        model.addAttribute("floresEmEstoque", floresEmEstoque);
        model.addAttribute("floresSemEstoque", floresSemEstoque);

        return "admin";
    }

    //restaura as flores que estiverem com estoque
    @GetMapping("/restaurarEstoque/{id}")
    public String RestaurarEstoque(@PathVariable Long id){
        adminService.restaurarEstoque(id);
        return "redirect:/admin";
    }

    //adicionar flores
    @PostMapping("/addFlores")
    public String addFlores(@ModelAttribute Flores flores,
                            @RequestParam("imagens") MultipartFile[] imagens){
        try {
            List<String> nomesArquivos = new ArrayList<>();
            for (MultipartFile imagem : imagens) {
               String nomeArquivo = imgService.armazenarImg(imagem);
                nomesArquivos.add(nomeArquivo);
            }
            flores.setUrlImagens(nomesArquivos);
            adminService.addFlor(flores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    }

    //editar flores
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model){
        return adminService.buscarPeloId(id).map(flores -> {
            model.addAttribute("flores", flores);
            return "editFlores";
        })
                .orElse("redirect:/admin");
    }

    //atualizar flores
    @PostMapping("/atualizarFlores/{id}")
    public String atualizarFlores(@PathVariable Long id,
                                  @ModelAttribute Flores florAtualizada,
                                  @RequestParam("imagens") MultipartFile[] imagens){
        try {
            if (imagens.length > 0) {
                List<String> caminhoImagens = imgService.armazenarImg(imagens);
                florAtualizada.setUrlImagens(caminhoImagens);
            }
            adminService.atualizarFlores(id, florAtualizada);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    }

    //deletar Flores
    @GetMapping("/deletarFlores/{id}")
    public String deletarFlores(@PathVariable Long id){
        adminService.deletarFlores(id);
        return "redirect:/admin";
    }

    //ver as vendas no controle de Vendas
    @GetMapping("controleVenda")
    public String exibirCompras(Model model){
        List<Carrinho> carrinhos = carrinhoService.exibirCompras();

        model.addAttribute("carrinhos", carrinhos);
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
