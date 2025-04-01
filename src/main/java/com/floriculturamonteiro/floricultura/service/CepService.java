package com.floriculturamonteiro.floricultura.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CepService { // não utilizável

    private final RegioesAtendidasService regiaoService;

    public boolean cepAtendido(String regiao){
        return regiaoService.regiaoAtendida(regiao);
    }

    public BigDecimal getPrecoEntrega(String regiao){
        return regiaoService.getPrecoEntrega(regiao);
    }
}
