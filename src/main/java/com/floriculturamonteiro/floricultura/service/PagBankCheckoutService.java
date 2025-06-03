package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.checkout.Checkout;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.Links;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.enums.LinkRelation;
import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PagBankCheckoutService {

    private final RestTemplate restTemplate;
    private final PagBankMapperService pagBankMapperService;

    @Value("${pagBank.api.url}")
    private String pagBankUrl;

    @Value("${pagBank.api.bearer_token}")
    private String bearerToken;

    public Checkout criarCheckout(Carrinho carrinho) {
        Checkout checkoutRequest = pagBankMapperService.convertToPagBankCheckout(carrinho);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Checkout> request = new HttpEntity<>(checkoutRequest, headers);
        ResponseEntity<Checkout> response = restTemplate
                .postForEntity(
                        pagBankUrl,
                        request,
                        Checkout.class);

        return response.getBody();
    }


    public String responsePagBank(Carrinho carrinho){
        Checkout response = criarCheckout(carrinho);

        return response.getLinks().stream()
                .filter(link -> LinkRelation.PAY.equals(link.getRel()))
                .findFirst().map(Links::getHref)
                .orElseThrow(() -> new RuntimeException("Link nao encontrado"));
    }
}
