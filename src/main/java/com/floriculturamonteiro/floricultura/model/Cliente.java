package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente", schema = "public")
@Data
public class Cliente {
 //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(name = "email")
    private String email;

    @Column(name = "cartao_mensagem")
    private String cartaoMensagemDestinatario;

    @Column(name = "incluir_cartao_mensagem")
    private boolean incluirCartaoMensagem = false;

    //métodos getters e setters gerados pelo lombok
}
