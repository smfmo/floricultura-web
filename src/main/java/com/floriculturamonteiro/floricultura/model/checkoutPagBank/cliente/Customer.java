package com.floriculturamonteiro.floricultura.model.checkoutPagBank.cliente;

import lombok.Data;


@Data
public class Customer {

    private String name;

    private String email;

    private String tax_id;

    private Phone phone;
}
