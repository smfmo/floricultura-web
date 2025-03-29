package com.floriculturamonteiro.floricultura.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Carrinho {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "data_hora_compra")
    private LocalDateTime dataHoraCompra;

    @Column(precision = 10, scale = 2, name = "sub_total_itens")
    private BigDecimal subTotalItens;

    @Column(precision = 10, scale = 2, name = "valor_cartao_mensagem")
    private BigDecimal valorCartaoMensagem;

    @Column(precision = 10, scale = 2, name = "valor_entrega")
    private BigDecimal valorEntrega;

    @Column(precision = 10, scale = 2, name = "total_final")
    private BigDecimal totalFinal;

    @Column(name = "concluido")
    private boolean concluido;

    //métodos getters e setters gerados pelo lombok

   //calcular o subtotal dos itens
    public BigDecimal calcularSubTotalItens() {
        return this.itens.stream()
                .map(item -> item.getPrecoTotal()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
