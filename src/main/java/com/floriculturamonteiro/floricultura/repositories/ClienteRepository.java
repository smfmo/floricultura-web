package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
