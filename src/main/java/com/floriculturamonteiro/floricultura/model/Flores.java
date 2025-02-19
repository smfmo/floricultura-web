package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;


@Entity(name = "flores")
@Table(name = "flores")
public class Flores {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //id gerado automáticamente automático


    private String nome;

    private String urlImagem;

    private Double preco;

    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(columnDefinition = "TEXT")
    private String cuidados;

    private Boolean emEstoque = true;

    //construtor
    public Flores(Long id,
                  String nome,
                  String urlImagem,
                  Double preco,
                  String descricao,
                  String cuidados) {
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.descricao = descricao;
        this.cuidados = cuidados;

    }

    public Flores(String nome,
                  String urlImagem,
                  String preco,
                  Double descricao,
                  String cuidados,
                  String cor,
                  String disponibilidade,
                  String embalagem) {

    }

    public Flores(String nome,
                  String urlImagem,
                  Double preco,
                  String descricao,
                  String cuidados,
                  String cor,
                  String disponibilidade,
                  String embalagem) {

    }

    public Flores() {

    }


    //métodos getters e setters
    public Boolean getEmEstoque() {
        return emEstoque;
    }

    public void setEmEstoque(Boolean emEstoque) {
        this.emEstoque = emEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
