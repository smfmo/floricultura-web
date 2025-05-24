package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.pedido.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    List<ItemCarrinho> findByCarrinhoId(Long carrinhoId);
}
