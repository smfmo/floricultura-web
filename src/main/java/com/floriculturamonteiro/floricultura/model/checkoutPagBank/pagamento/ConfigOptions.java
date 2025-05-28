package com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.Option;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ConfigOptions {

    @Enumerated(EnumType.STRING)
    private Option option;

    private String value;
}
