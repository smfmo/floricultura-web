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

@Entity
@Data
@Table(name = "checkouts",
        schema = "public")
public class Checkout {

    private String checkout_id; //id gerado pelo Pagbank

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reference_id",
            length = 64)
    private UUID reference_id;

    @CreatedDate
    @Column(name = "expiration_date")
    private LocalDateTime expiration_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "customer_modifiable")
    private Boolean customer_modifiable = true;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "checkout_id")
    private List<Item> items = new ArrayList<>();

    @Column(name = "additional_amount")
    private Integer additional_amount;

    @Column(name = "discount_amount")
    private Integer discount_amount;

    //Shipping caso dê erro

    @OneToMany(cascade = CascadeType.ALL)
    @CollectionTable(name = "checkout_payment_methods",
            joinColumns = @JoinColumn(name = "checkout_id"))
    private List<PaymentMethods> payment_methods = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "checkout_id")
    private List<PaymentMethodConfig> payment_methods_configs = new ArrayList<>();

    @Column(name = "redirect_url")
    private String redirect_url;

    @Column(name = "soft_descriptor",
            length = 17)
    private String soft_descriptor;

    @Column(name = "return_url",
            length = 255)
    private String return_url;

    @ElementCollection
    @CollectionTable(name = "notification_urls",
            joinColumns = @JoinColumn(name = "checkout_id"))
    private List<String> notification_urls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "checkout_payment_notification_urls",
            joinColumns = @JoinColumn(name = "checkout_id"))
    private List<String> payment_notifications_urls = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "checkout_id")
    private List<Links> links = new ArrayList<>();

}
