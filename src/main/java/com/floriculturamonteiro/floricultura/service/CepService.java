package com.floriculturamonteiro.floricultura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
//refatorar para localizações estáticas - retirar API de Ceps

@Service
public class CepService {

    private final RegioesAtendidasService regiaoService;

    @Autowired
    public CepService(RegioesAtendidasService regiaoService) {
        this.regiaoService = regiaoService;
    }

    public boolean cepAtendido(String regiao){
        return regiaoService.regiaoAtendida(regiao);
    }
    public BigDecimal getPrecoEntrega(String regiao){
        return regiaoService.getPrecoEntrega(regiao);
    }
}
