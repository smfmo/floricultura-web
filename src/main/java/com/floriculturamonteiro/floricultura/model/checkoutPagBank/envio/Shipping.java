package com.floriculturamonteiro.floricultura.model.checkoutPagBank.envio;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.cliente.Address;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.envio.enums.ShippingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class Shipping {

    @Enumerated(EnumType.STRING)
    private ShippingType type;

    private Address address;

    private Box box;
}
