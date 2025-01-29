package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;

@Entity(name = "flores")
@Table(name = "flores")
public class Flores {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //id gerado automáticamente automático

    private String nome;
    private String descricao;
    private String cuidados;
    private Long valor;
    private String disponibilidade;
    private String cor;
    private String embalagem;

    //construtor
    public Flores(Integer id,
                  String nome,
                  String descricao,
                  String cuidados,
                  Long valor,
                  String disponibilidade,
                  String cor,
                  String embalagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cuidados = cuidados;
        this.valor = valor;
        this.disponibilidade = disponibilidade;
        this.cor = cor;
        this.embalagem = embalagem;
    }

    public Flores() {

    }


    //métodos getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
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
}
