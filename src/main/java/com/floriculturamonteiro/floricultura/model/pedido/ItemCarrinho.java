package com.floriculturamonteiro.floricultura.model.pedido;

import com.floriculturamonteiro.floricultura.model.produto.Flores;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table (name = "Item_carrinho",
        schema = "public")
@Data
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

    @Column(precision = 10, scale = 2, name = "valor_unitario")
    private BigDecimal valorUnitario;

    //métodos getters e setters gerados pelo lombok

}
