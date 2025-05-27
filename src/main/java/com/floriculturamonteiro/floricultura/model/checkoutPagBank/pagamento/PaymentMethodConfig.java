package com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.PaymentMethodType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import java.util.Set;

@Data
public class PaymentMethodConfig {
    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    private Set<ConfigOptions> config_options;
}
