package com.floriculturamonteiro.floricultura.repositories;

import com.floriculturamonteiro.floricultura.model.usuarios.UserAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAdmRepository extends JpaRepository<UserAdm, Long> {
    Optional<UserAdm> findByUsername(String username);
}
