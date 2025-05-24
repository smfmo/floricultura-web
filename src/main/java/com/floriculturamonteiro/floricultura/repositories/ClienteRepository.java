package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
