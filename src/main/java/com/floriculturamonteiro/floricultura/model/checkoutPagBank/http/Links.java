package com.floriculturamonteiro.floricultura.model.checkoutPagBank.http;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.HttpMethod;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.LinkRelation;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Links {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rel")
    private LinkRelation rel;

    @Column(name = "href")
    private String href;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private HttpMethod method;
}
