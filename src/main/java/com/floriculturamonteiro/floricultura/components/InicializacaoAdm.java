package com.floriculturamonteiro.floricultura.components;

import com.floriculturamonteiro.floricultura.service.UserAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializacaoAdm implements CommandLineRunner {


    private final UserAdmService userAdmService;

    @Autowired
    public InicializacaoAdm(UserAdmService userAdmService) {
        this.userAdmService = userAdmService;
    }

    public void run (String... args) {
        //criação do usuário administrador
        userAdmService.criarUsuarioAdmin("gkkm2006", "g@briel2006");
    }
}
