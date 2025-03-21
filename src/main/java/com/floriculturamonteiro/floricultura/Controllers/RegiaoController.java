package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.service.RegioesAtendidasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/regioes")
public class RegiaoController {

    private final RegioesAtendidasService regiaoService;

    public RegiaoController(RegioesAtendidasService regiaoService) {
        this.regiaoService = regiaoService;
    }

    @GetMapping
    public Map<String, BigDecimal> listarRegioesAtendidas() {
        return regiaoService.getRegioesAtendidas();
    }
}
