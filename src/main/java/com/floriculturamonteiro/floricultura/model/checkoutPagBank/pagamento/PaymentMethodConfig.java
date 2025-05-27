package com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
public class PaymentMethodConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentMethodType type;

    @ElementCollection
    @CollectionTable(name = "options",
            joinColumns = @JoinColumn(name = "payment_method_config_id"))
    private Set<ConfigOptions> config_options;
}
