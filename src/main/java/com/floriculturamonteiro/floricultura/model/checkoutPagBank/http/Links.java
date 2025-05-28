package com.floriculturamonteiro.floricultura.model.checkoutPagBank.http;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.HttpMethod;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.LinkRelation;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class Links {


    @Enumerated(EnumType.STRING)
    private LinkRelation rel;

    private String href;

    @Enumerated(EnumType.STRING)
    private HttpMethod method;
}
