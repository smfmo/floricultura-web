package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    //atributo
    private final FloresRepository repository;

    //adicionar as flores
    public void addFlor(Flores flores){
        repository.save(flores);
    }

    //buscar pelo ID
    public Optional<Flores> buscarPeloId(Long id){
        return repository.findById(id);
    }

    //atualizar flores
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

    //buscar somente flores que estão em estoque
    public List<Flores> buscarFloresEmEstoque(){
        return repository.findByEmEstoqueTrue();
    }

    //buscar flores que não estão em estoque
    public List<Flores> buscarFloresSemEstoque(){
        return repository.findByEmEstoqueFalse();
    }

    //restaurar as flores
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
