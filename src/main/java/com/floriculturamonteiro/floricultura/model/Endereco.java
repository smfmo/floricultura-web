package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco", schema = "public")
@Data
public class Endereco {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    //metodos getter e setter gerados pelo Lombok
}
