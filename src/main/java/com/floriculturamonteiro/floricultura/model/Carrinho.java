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

    @Column(name = "total_carrinho")
    private BigDecimal totalCarrinho;

    @Column(name = "concluido")
    private boolean concluido;


    //métodos getters e setters gerados pelo lombok
}
