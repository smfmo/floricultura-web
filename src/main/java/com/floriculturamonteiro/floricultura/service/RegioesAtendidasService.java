package com.floriculturamonteiro.floricultura.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RegioesAtendidasService {

    private static final Map<String, BigDecimal> REGIOES_ATENDIDAS = new LinkedHashMap<>();
    static {
        REGIOES_ATENDIDAS.put("Santa Maria", new BigDecimal("30"));
        REGIOES_ATENDIDAS.put("Céu Azul", new BigDecimal("20"));
        REGIOES_ATENDIDAS.put("Valparaíso 2", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Jardim Oriente", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Morada Nobre", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Setor A", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Setor B", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Setor C", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Setor D", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Setor E", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Parque esplanada 1", new BigDecimal("20"));
        REGIOES_ATENDIDAS.put("Parque esplanada 2", new BigDecimal("20"));
        REGIOES_ATENDIDAS.put("Parque esplanada 3", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Parque Rio Branco", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Bairro das Palmeiras", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Anhaguera 1", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Anhaguera 2", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Jardim Ipanema", new BigDecimal("20"));
        REGIOES_ATENDIDAS.put("Cidade Jardins", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Chácara Anhaguera 1", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Chácara Anhaguera 2", new BigDecimal("15"));
        REGIOES_ATENDIDAS.put("Residencias Florais", new BigDecimal("20"));
        REGIOES_ATENDIDAS.put("Mansões recreio Mossoró", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Parque Araguari 2", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Jardim Flamboyant", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Parque Nápolis", new BigDecimal("0"));
        REGIOES_ATENDIDAS.put("Jardim Zuleika", new BigDecimal("25"));
        REGIOES_ATENDIDAS.put("Parque Marajó", new BigDecimal("25"));
        REGIOES_ATENDIDAS.put("Shopping Sul", new BigDecimal("25"));
    }

    public boolean regiaoAtendida(String regiao){
        return REGIOES_ATENDIDAS.containsKey(regiao);
    }

    public BigDecimal getPrecoEntrega(String regiao){
        return REGIOES_ATENDIDAS.getOrDefault(regiao, BigDecimal.ZERO);
    }

    public Map<String, BigDecimal> getRegioesAtendidas() {
        return REGIOES_ATENDIDAS;
    }
}
