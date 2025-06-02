package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.pedido.Carrinho;
import com.floriculturamonteiro.floricultura.model.usuarios.Cliente;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void conteudoEmail(String para,
                            String assunto,
                            String templateName,
                            Context context) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                true, "UTF-8");

        String conteudoHtml = templateEngine.process(templateName, context);

        mimeMessageHelper.setTo(para);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(conteudoHtml, true);

        ClassPathResource classPathResource = new ClassPathResource("static/img/floriculturaMonteiro.jpg");
        mimeMessageHelper.addInline("floriculturaMonteiro", classPathResource);
        mailSender.send(mimeMessage);
    }

    public void enviarEmailAposCompra(Cliente cliente, Carrinho carrinho) throws MessagingException {
        //enviar o email após finalização da compra
        Context context = new Context();
        context.setVariable("clienteNome", cliente.getNome());
        context.setVariable("statusCarrinho", "compra finalizada");
        context.setVariable("totalCarrinho", carrinho.getTotalFinal());
        context.setVariable("itens", carrinho.getItens());
        context.setVariable("incluirCartaoMensagem", carrinho.isIncluirCartaoMensagem());

        conteudoEmail(cliente.getEmail(), "status do seu pedido", "email", context);
    }
}
