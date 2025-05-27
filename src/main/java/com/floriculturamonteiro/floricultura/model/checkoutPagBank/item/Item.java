package com.floriculturamonteiro.floricultura.model.checkoutPagBank.item;

import lombok.Data;

@Data
public class Item {

    private String reference_id;

    private String name;

    private String description;

    private Integer quantity;

    private Integer unit_amount;

    private String image_url;
}
