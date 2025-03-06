package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Query("SELECT DISTINCT c FROM Carrinho c " +
            "LEFT JOIN FETCH c.itens " +
            "LEFT JOIN FETCH c.cliente " +
            "WHERE SIZE(c.itens) > 0")
    List<Carrinho> findAllWithItens();

    List<Carrinho> findByConcluido(boolean concluido);
    List<Carrinho> findByItens(List<ItemCarrinho> itens);
}
