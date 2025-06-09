package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.produto.Enum.CategoriaProduto;
import com.floriculturamonteiro.floricultura.model.produto.Flores;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminController {

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
        model.addAttribute("categoriaProduto", CategoriaProduto.values());

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
            model.addAttribute("categoriaProduto", CategoriaProduto.values());
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
            Flores floresExistentes = adminService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Flor não encontrada"));

            List<MultipartFile> imagensValidas = Arrays.stream(imagens)
                    .filter(img -> !img.isEmpty())
                    .toList();

            if (!imagensValidas.isEmpty()) {
                List<String> caminhoImagens = imgService.armazenarImg(imagensValidas.toArray(new MultipartFile[0]));
                florAtualizada.setUrlImagens(caminhoImagens);
            }else {
                florAtualizada.setUrlImagens(floresExistentes.getUrlImagens());
            }
            adminService.atualizarFlores(id, florAtualizada);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar imagens", e);
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
