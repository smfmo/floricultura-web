package com.floriculturamonteiro.floricultura.model.pedido;

import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
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
    @JoinColumn(name = "cliente_id",
            referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "data_hora_compra")
    private LocalDateTime dataHoraCompra;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "finalizado")
    private boolean finalizado;

    @Column(precision = 10,
            scale = 2,
            name = "sub_total_itens")
    private BigDecimal subTotalItens;

    @Column(precision = 10,
            scale = 2,
            name = "valor_cartao_mensagem")
    private BigDecimal valorCartaoMensagem;

    @Column(precision = 10,
            scale = 2,
            name = "valor_entrega")
    private BigDecimal valorEntrega;

    @Column(precision = 10,
            scale = 2,
            name = "total_final")
    private BigDecimal totalFinal;

    @Column(name = "concluido")
    private boolean concluido;

    @Column(name = "cartao_mensagem")
    private String cartaoMensagemDestinatario;

    @Column(name = "incluir_cartao_mensagem")
    private boolean incluirCartaoMensagem = false;

    //métodos getters e setters gerados pelo lombok

   //calcular o subtotal dos itens
    public BigDecimal calcularSubTotalItens() {
        return this.itens.stream()
                .map(item -> item.getValorUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
