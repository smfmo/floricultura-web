package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.checkoutPagBank.checkout.Checkout;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.cliente.Customer;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.item.Item;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.ConfigOptions;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.PaymentMethodConfig;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.PaymentMethods;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.Option;
import com.floriculturamonteiro.floricultura.model.checkoutPagBank.pagamento.enums.PaymentMethodType;
import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.pedido.ItemCarrinho;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Esta classe vai converter o objeto "checkout" para os objetos da aplicação.
 */

@Service
public class PagBankMapperService {

    public Checkout convertToPagBankCheckout(Carrinho carrinho) {
        Checkout checkout = new Checkout();

        checkout.setReference_id(UUID.randomUUID().toString());
        //checkout.setExpiration_date(LocalDateTime.now().plusHours(24));
        //checkout.setCreated_at(LocalDateTime.now());

        checkout.setCustomer(convertClienteToCustomer(carrinho.getCliente())); //convertendo Customer para cliente

        checkout.setItems(convertItensCarrinhoToPagBankItems(carrinho.getItens(),carrinho)); //converte Item para itens do carrinho

        //checkout.setRedirect_url("http://localhost:8080/carrinho/confirmacao");
        //checkout.setReturn_url("http://localhost:8080/");
        //checkout.setNotification_urls(List.of(""));

        checkout.setCustomer_modifiable(true);
        checkout.setAdditional_amount(0);
        checkout.setDiscount_amount(0);
        checkout.setPayment_methods(paymentMethods());
        checkout.setPayment_methods_configs(paymentMethodConfigs());

        return checkout;
    }


    private Customer convertClienteToCustomer(Cliente cliente) {
        Customer customer = new Customer();
        customer.setName(cliente.getNome());
        customer.setEmail(cliente.getEmail());
        customer.setTax_id(cliente.getCpf());
        return customer;
    }

    private List<Item> convertItensCarrinhoToPagBankItems(List<ItemCarrinho> itens, Carrinho carrinho) {

        // 1. adiciona os produtos normalmente
        List<Item> items = new ArrayList<>(
                itens
                .stream()
                .map(this::convertItem)
                .toList());

        // 2. Adiciona o cartão mensagem como item adicional
        if (carrinho.isIncluirCartaoMensagem() && carrinho.getValorCartaoMensagem().compareTo(BigDecimal.ZERO) > 0) {
            items.add(createItemAditional(
                    "cartão mensagem",
                    carrinho.getValorCartaoMensagem()
            ));
        }

        // 3. Frete
        if (carrinho.getValorEntrega() != null && carrinho.getValorEntrega().compareTo(BigDecimal.ZERO) > 0) {
            items.add(createItemAditional(
                    "Taxa de entrega - " + carrinho.getCliente()
                    .getEndereco()
                    .getRegiao(),
                    carrinho.getValorEntrega()
                    )
            );

        }
        return items;
    }

    private Item convertItem(ItemCarrinho itemCarrinho) {
        Item item = new Item();
        item.setName(itemCarrinho.getNomeProduto());
        item.setQuantity(itemCarrinho.getQuantidade());
        item.setUnit_amount(itemCarrinho.getValorUnitario()
                .multiply(BigDecimal
                        .valueOf(100)).intValue());
        return item;
    }

    private Item createItemAditional(String nome,
                                     BigDecimal valor) {
        Item item = new Item();
        item.setName(nome);
        item.setQuantity(1);
        item.setUnit_amount(valor
                .multiply(BigDecimal.valueOf(100)).intValue());
        return item;
    }

    private List<PaymentMethods> paymentMethods(){
        List<PaymentMethods> paymentMethods = new ArrayList<>();

        PaymentMethods creditCard = new PaymentMethods();
        creditCard.setType(PaymentMethodType.CREDIT_CARD);

        PaymentMethods debitCard = new PaymentMethods();
        debitCard.setType(PaymentMethodType.DEBIT_CARD);

        PaymentMethods pix = new PaymentMethods();
        pix.setType(PaymentMethodType.PIX);

        PaymentMethods boleto = new PaymentMethods();
        boleto.setType(PaymentMethodType.BOLETO);

        paymentMethods.add(creditCard);
        paymentMethods.add(debitCard);
        paymentMethods.add(pix);
        paymentMethods.add(boleto);

        return paymentMethods;
    }

    private List<PaymentMethodConfig> paymentMethodConfigs(){
        List<PaymentMethodConfig> paymentMethodConfigs = new ArrayList<>();

        ConfigOptions creditCardInstallments = new ConfigOptions();
        creditCardInstallments.setOption(Option.INSTALLMENTS_LIMIT);
        creditCardInstallments.setValue("12"); //parcelas

        ConfigOptions creditCardInterestFree = new ConfigOptions();
        creditCardInterestFree.setOption(Option.INTEREST_FREE_INSTALLMENTS);
        creditCardInterestFree.setValue("3");

        ConfigOptions debitCardInstallments = new ConfigOptions();
        debitCardInstallments.setOption(Option.INSTALLMENTS_LIMIT);
        debitCardInstallments.setValue("1"); //à vista (1 parcela)

        PaymentMethodConfig creditCardConfig = new PaymentMethodConfig();
        creditCardConfig.setType(PaymentMethodType.CREDIT_CARD);
        creditCardConfig.setConfig_options(new HashSet<>());
        creditCardConfig.getConfig_options().add(creditCardInstallments);
        //creditCardConfig.getConfig_options().add(creditCardInterestFree);

        //PaymentMethodConfig debitCardConfig = new PaymentMethodConfig();
        //debitCardConfig.setType(PaymentMethodType.DEBIT_CARD);
        //debitCardConfig.getConfig_options().add(debitCardInstallments);

        paymentMethodConfigs.add(creditCardConfig);
        return paymentMethodConfigs;
    }
}
