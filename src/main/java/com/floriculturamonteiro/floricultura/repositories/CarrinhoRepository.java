package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Query("""
    select distinct c
    from Carrinho c
    left join fetch c.itens
    left join fetch  c.cliente
    where size(c.itens) > 0
    """)
    List<Carrinho> EncontrarTodosComItens();

    @Query("""
    select c
    from Carrinho c
    join c.cliente
    cl where lower(cl.nome) = lower(:nome)
    """)
    List<Carrinho> findByNomeClienteIgnoreCase(@Param("nome") String nome);

    List<Carrinho> findByConcluido(boolean concluido);
    
}
