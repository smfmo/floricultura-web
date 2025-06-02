package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.produto.Flores;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final FloresRepository repository;

    public void addFlor(Flores flores){
        repository.save(flores);
    }

    public Optional<Flores> buscarPeloId(Long id){
        return repository.findById(id);
    }

    public void atualizarFlores(Long id, Flores florAtualizada){
        repository.findById(id).ifPresent(flores ->{
            flores.setNome(florAtualizada.getNome());
            flores.setUrlImagens(florAtualizada.getUrlImagens());
            flores.setPreco(florAtualizada.getPreco());
            flores.setDescricao(florAtualizada.getDescricao());
            flores.setCuidados(florAtualizada.getCuidados());
            flores.setCategoriaProduto(florAtualizada.getCategoriaProduto());
            repository.save(flores);
        });
    }

    //deletar flores
    public void deletarFlores(Long id){
        repository.findById(id).ifPresent(flores ->{
            flores.setEmEstoque(false);
            repository.save(flores);
        });
    }

    public List<Flores> buscarFloresEmEstoque(){
        return repository.findByEmEstoqueTrue();
    }

    public List<Flores> buscarFloresSemEstoque(){
        return repository.findByEmEstoqueFalse();
    }

    public void restaurarEstoque(Long id){
        repository.findById(id).ifPresent(flores ->{
            flores.setEmEstoque(true);
            repository.save(flores);
        });
    }

    public Optional<Flores> buscarPorId(Long id){
        return repository.findById(id);
    }

    /*listar as flores (se precisar listar todas as flores cadastradas!!)
    @Transactional(readOnly = true) //apenas leitura
    public List<Flores> getAllFlores(){
        return repository.findAll();
    }*/

}
