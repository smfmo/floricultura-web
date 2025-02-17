package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Flores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloresRepository extends JpaRepository<Flores, Long> {
    List<Flores> findByEmEstoqueTrue();
    List<Flores> findByEmEstoqueFalse();
}
