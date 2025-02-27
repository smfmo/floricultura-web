package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table (name = "Item_carrinho", schema = "public")
@Getter
@Setter
public class ItemCarrinho {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Flores flores; //flores_id (chave estrangeira)

    @Column(name = "quantidade")
    private int quantidade;

    @ManyToOne
    private Carrinho carrinho; //carrinho_id (chave estrangeira)

    @Column(name = "preco_total")
    private BigDecimal precoTotal;

    @Column(name = "nome_produto")
    private String nomeProduto;

    //métodos getters e setters gerados pelo lombok

}
