package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.model.Endereco;
import com.floriculturamonteiro.floricultura.service.CepService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class CepController {
    @Autowired
    private CepService cepService;

    @GetMapping("/{cep}")
    public Endereco getEndereco(@PathVariable String cep) {
        return cepService.buscarEnderecoPeloCep(cep);
    }
}
