package com.floriculturamonteiro.floricultura.model.checkoutPagBank.cliente;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String number;
    private String complement;
    private String locality;
    private String city;
    private String region_code;
    private String country;
    private String postal_code;
}
