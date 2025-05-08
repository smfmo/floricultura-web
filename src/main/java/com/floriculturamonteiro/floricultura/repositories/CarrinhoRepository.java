package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Query("""
    select distinct c
    from Carrinho c
    left join fetch c.itens
    left join fetch  c.cliente
    where size(c.itens) > 0
    """)
    Page<Carrinho> EncontrarTodosComItens(Pageable pageable);

    @Query("""
    select c
    from Carrinho c
    join c.cliente
    cl where lower(cl.nome) like lower(concat('%', :nome, '%'))
    """)
    Page<Carrinho> findByNomeClienteIgnoreCase(@Param("nome") String nome, Pageable pageable);

    List<Carrinho> findByConcluido(boolean concluido);

    List<Carrinho> findByClienteIn(List<Cliente> clientes);

    List<Carrinho> findByUltimaAtualizacaoBeforeAndFinalizadoFalse(LocalDateTime dataLimite);

}
