package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.ArmazenamentoImgService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public  class AdminController {

    private final AdminService adminService;
    private final ArmazenamentoImgService imgService;
    private final CarrinhoService carrinhoService;

    @Autowired
    public AdminController(AdminService adminService,
                           ArmazenamentoImgService imgService,
                           CarrinhoService carrinhoService) {
        this.adminService = adminService;
        this.imgService = imgService;
        this.carrinhoService = carrinhoService;
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
                            @RequestParam("imagem") MultipartFile imagem){
        try {
            //salva a imagem e obtém o caminho
            String nomeArquivo = imgService.armazenarImg(imagem);
            flores.setUrlImagem(nomeArquivo);

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
                                  @RequestParam("imagem") MultipartFile imagem){

        try {
            //se uma nova imagem for adicionada ao editar a flor, vai salcar e atualizar o caminho
            if (!imagem.isEmpty()) {
                String caminhoImg = imgService.armazenarImg(imagem);
                florAtualizada.setUrlImagem(caminhoImg);

                //atualiza o produto normalmente no banco de dados
                adminService.atualizarFlores(id, florAtualizada);
            }
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
        for (Carrinho carrinho : carrinhos) {
            double totalCarrinho = carrinho.getItens().stream()
                    .mapToDouble(ItemCarrinho::getPrecoTotal).sum();
            carrinho.setTotalCarrinho(totalCarrinho);
        }
        model.addAttribute("carrinhos", carrinhoService.exibirCompras());
        return "controleVenda";
    }
}
