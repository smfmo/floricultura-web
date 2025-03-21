package com.floriculturamonteiro.floricultura.model;

import com.floriculturamonteiro.floricultura.service.RegioesAtendidasService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco",
        schema = "public")
@Data
public class Endereco {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;
    private String regiao;

    //metodos getter e setter gerados pelo Lombok
}
