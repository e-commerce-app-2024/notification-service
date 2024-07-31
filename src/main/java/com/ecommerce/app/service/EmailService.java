package com.ecommerce.app.service;

import com.ecommerce.app.dto.order.OrderConfirmation;
import com.ecommerce.app.dto.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.ecommerce.app.enums.EmailTemplatesEnum.ORDER_CONFIRMATION;
import static com.ecommerce.app.enums.EmailTemplatesEnum.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${app.admin.email}")
    private String from;

    @Async
    public void sendPaymentEmail(PaymentConfirmation paymentConfirmation) {
        String to = paymentConfirmation.customerEmail();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
            messageHelper.setFrom(from);
            messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());
            String htmlTemplate = prepareHtmlTemplate(preparePaymentConfirmationModel(paymentConfirmation), PAYMENT_CONFIRMATION.getTemplate());
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(htmlTemplate);
            log.info(String.format("Sending payment confirmation email successfully to: %s", to));
        } catch (MessagingException e) {
            log.warn("WARNING: Unable to send payment confirmation email to {}", to, e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(OrderConfirmation orderConfirmation) {
        String to = orderConfirmation.customer().email();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
            messageHelper.setFrom(from);
            messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());
            String htmlTemplate = prepareHtmlTemplate(prepareOrderConfirmationModel(orderConfirmation), ORDER_CONFIRMATION.getTemplate());
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(htmlTemplate);
            log.info(String.format("Sending payment confirmation email successfully to: %s", to));
        } catch (MessagingException e) {
            log.warn("WARNING: Unable to send order confirmation email to {}", to, e);
        }
    }

    private String prepareHtmlTemplate(Map<String, Object> model, String templateName) {
        Context context = new Context();
        context.setVariables(model);
        return templateEngine.process(templateName, context);
    }

    private Map<String, Object> preparePaymentConfirmationModel(PaymentConfirmation paymentConfirmation) {
        Map<String, Object> model = new HashMap<>();
        model.put("amount", paymentConfirmation.amount().setScale(2, BigDecimal.ROUND_HALF_UP));
        model.put("orderReference", paymentConfirmation.orderReference());
        model.put("customerName", paymentConfirmation.customerName());
        return model;
    }

    private Map<String, Object> prepareOrderConfirmationModel(OrderConfirmation orderConfirmation) {
        Map<String, Object> model = new HashMap<>();
        model.put("totalAmount", orderConfirmation.totalAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        model.put("orderReference", orderConfirmation.orderReference());
        model.put("customerName", orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName());
        model.put("products", orderConfirmation.products());
        return model;
    }
}
