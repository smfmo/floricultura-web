package com.floriculturamonteiro.floricultura.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender,
                        TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void enviarEmail(String para,
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
}
