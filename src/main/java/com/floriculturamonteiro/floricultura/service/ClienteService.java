package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Cliente;
import com.floriculturamonteiro.floricultura.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //metodo para salvar o cliente
    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }
}
