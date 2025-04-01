package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    //metodo para salvar o cliente
    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }
}
