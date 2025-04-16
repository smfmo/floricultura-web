package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Enum.CategoriaProduto;
import com.floriculturamonteiro.floricultura.model.Flores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FloresRepository extends JpaRepository<Flores, Long>, JpaSpecificationExecutor<Flores> {
    List<Flores> findByEmEstoqueTrue();
    List<Flores> findByEmEstoqueFalse();
    List<Flores> findByCategoriaProdutoAndEmEstoqueTrue(CategoriaProduto categoriaProduto);


}
