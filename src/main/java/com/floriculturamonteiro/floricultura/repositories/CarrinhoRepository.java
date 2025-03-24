package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    List<Carrinho> findByConcluido(boolean concluido);

    //List<Carrinho> findByItens(List<ItemCarrinho> itens);
}
