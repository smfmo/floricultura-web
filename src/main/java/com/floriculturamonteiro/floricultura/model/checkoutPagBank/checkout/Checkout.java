package com.floriculturamonteiro.floricultura.model.checkoutPagBank.checkout;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.checkout.enums.CheckoutStatus;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.cliente.Customer;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.http.Links;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.item.Item;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.PaymentMethodConfig;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.PaymentMethods;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Checkout {

    private String checkout_id; //id gerado pelo Pagbank

    private String reference_id;

    private LocalDateTime expiration_date;

    private Customer customer;

    private Boolean customer_modifiable = true;

    private List<Item> items = new ArrayList<>();

    private Integer additional_amount;

    private Integer discount_amount;

    //Shipping

    private List<PaymentMethods> payment_methods = new ArrayList<>();

    private List<PaymentMethodConfig> payment_methods_configs = new ArrayList<>();

    private String redirect_url;

    private String soft_descriptor;

    private String return_url;

    private List<String> notification_urls = new ArrayList<>();

    private List<String> payment_notifications_urls = new ArrayList<>();


    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    private List<Links> links = new ArrayList<>();

}
