package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity(name = "flores")
@Table(name = "flores", schema = "public")
@Getter
@Setter
public class Flores {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //id gerado automáticamente automático

    @Column(name = "nome")
    private String nome;

    @Column(name = "url_imagem")
    private String urlImagem;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "cuidados", columnDefinition = "TEXT")
    private String cuidados;

    @Column(name = "em_estoque")
    private Boolean emEstoque = true;

    //construtor
    public Flores(Long id,
                  String nome,
                  String urlImagem,
                  BigDecimal preco,
                  String descricao,
                  String cuidados) {
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.descricao = descricao;
        this.cuidados = cuidados;

    }

    public Flores() {

    }

    //métodos getters e setters gerados pelo lombok
}
