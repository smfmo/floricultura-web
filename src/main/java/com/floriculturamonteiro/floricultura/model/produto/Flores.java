package com.floriculturamonteiro.floricultura.model.produto;

import com.floriculturamonteiro.floricultura.model.produto.Enum.CategoriaProduto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Entity(name = "flores")
@Table(name = "flores",
        schema = "public")
@Data
@NoArgsConstructor
public class Flores {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //id gerado automáticamente automático

    @Column(name = "nome")
    private String nome;

    @ElementCollection
    @CollectionTable(name = "flores_imagens",
            joinColumns = @JoinColumn(name = "flores_id"))
    private List<String> urlImagens;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "cuidados", columnDefinition = "TEXT")
    private String cuidados;

    @Column(name = "em_estoque")
    private Boolean emEstoque = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_produto")
    private CategoriaProduto categoriaProduto;

    //construtor
    public Flores(Long id,
                  String nome,
                  List<String> urlImagens,
                  BigDecimal preco,
                  String descricao,
                  String cuidados) {
        this.id = id;
        this.nome = nome;
        this.urlImagens = urlImagens;
        this.preco = preco;
        this.descricao = descricao;
        this.cuidados = cuidados;

    }
    //métodos getters e setters gerados pelo lombok
}
