package com.floriculturamonteiro.floricultura.repositories.specs;

import com.floriculturamonteiro.floricultura.model.produto.Flores;
import org.springframework.data.jpa.domain.Specification;

public class FloresSpecs {

    public static Specification<Flores> nomeLike(String nome) {
        return (root,
                query,
                cb) -> cb.like(
                        cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }
}
