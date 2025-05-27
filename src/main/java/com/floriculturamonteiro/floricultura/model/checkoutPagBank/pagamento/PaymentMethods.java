package com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.Brands;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentMethodType type;

    @ElementCollection
    @CollectionTable(name = "brands",
            joinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<Brands> brands;

}
