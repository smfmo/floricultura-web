package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.service.AdminService;
import com.floriculturamonteiro.floricultura.service.ArmazenamentoImgService;
import com.floriculturamonteiro.floricultura.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ArmazenamentoImgService imgService;
    @Autowired
    private CarrinhoService carrinhoService;

    //página de catálogo
    @GetMapping
    public String catalogo(Model model){
        Carrinho carrinho = carrinhoService.criarCarrinho();
        model.addAttribute("carrinhoId", carrinho.getId());
        model.addAttribute("flores", adminService.getAllFlores());
        return "catalogo";
    }

    //página do administrador
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("flores", adminService.getAllFlores());
        return "admin";
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
}
