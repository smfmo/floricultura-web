package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private FloresRepository repository;

    public AdminService(FloresRepository repository) {
        this.repository = repository;
    }

    //listar as flores
    public List<Flores> getAllFlores(){
        return repository.findAll();
    }

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
            flores.setUrlImagem(florAtualizada.getUrlImagem());
            flores.setPreco(florAtualizada.getPreco());
            flores.setDescricao(florAtualizada.getDescricao());
            flores.setCuidados(florAtualizada.getCuidados());
            flores.setCor(florAtualizada.getCor());
            flores.setDisponibilidade(florAtualizada.getDisponibilidade());
            flores.setEmbalagem(florAtualizada.getEmbalagem());
            repository.save(flores);
        });
    }

    //deletar flores
    public void deletarFlores(Long id){
        repository.deleteById(id);
    }
}
