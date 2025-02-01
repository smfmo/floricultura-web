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

    private String descricao;

    private String cuidados;

    private String cor;

    private String disponibilidade;

    private String embalagem;


    //construtor
    public Flores(Long id,
                  String nome,
                  String urlImagem,
                  Double preco,
                  String descricao,
                  String cuidados,
                  String cor,
                  String disponibilidade,
                  String embalagem) {
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.descricao = descricao;
        this.cuidados = cuidados;
        this.cor = cor;
        this.disponibilidade = disponibilidade;
        this.embalagem = embalagem;
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

    public Flores(String nome, String urlImagem, Double preco, String descricao, String cuidados, String cor, String disponibilidade, String embalagem) {

    }

    public Flores() {

    }


    //métodos getters e setters
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

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
