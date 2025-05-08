package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Enum.CategoriaProduto;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.floriculturamonteiro.floricultura.repositories.specs.FloresSpecs.*;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final FloresRepository floresRepository;

    public List<Flores> pesquisaPorNome(String nome){
        Specification<Flores> specs = Specification
                .where((root,
                        query,
                        cb) -> cb.conjunction());

        if (nome != null) {
            specs = specs.and(nomeLike(nome));
        }

        specs = specs.and(((root,
                            query,
                            criteriaBuilder) -> criteriaBuilder.isTrue(root.get("emEstoque"))));

        return floresRepository.findAll(specs);
    }

    public List<Flores> pesquisaPorCategoria(CategoriaProduto categoria){
        return floresRepository.findByCategoriaProdutoAndEmEstoqueTrue(categoria);
    }
}
