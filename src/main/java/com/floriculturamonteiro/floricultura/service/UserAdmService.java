package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.usuarios.UserAdm;
import com.floriculturamonteiro.floricultura.repositories.UserAdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdmService {

    private final UserAdmRepository userAdmRepository;
    private final PasswordEncoder passwordEncoder;

    public void criarUsuarioAdmin(String username, String password){
        if (userAdmRepository.findByUsername(username).isPresent()) {
            //System.out.println("usuário " + username + "já existe. nada foi feito.");
            return;
        }
        UserAdm userAdm = new UserAdm();
        userAdm.setUsername(username);
        userAdm.setPassword(passwordEncoder.encode(password));
        userAdm.setRole("ADMIN"); //define o papel como ADMIN

        userAdmRepository.save(userAdm);
    }
}
