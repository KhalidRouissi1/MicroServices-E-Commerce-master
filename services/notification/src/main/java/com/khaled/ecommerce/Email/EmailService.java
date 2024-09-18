package com.khaled.ecommerce.Email;

import com.khaled.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.khaled.ecommerce.Email.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount,String orderReference) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        mimeMessageHelper.setFrom("contact@nvs.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables  = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderRefrence", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s",destinationEmail,templateName));
        }catch (MessagingException e){
            log.warn("WARNING - Cannot send email to {}",destinationEmail);
            e.getStackTrace();
        }
    }


    @Async
    public void sendOrderSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        mimeMessageHelper.setFrom("contact@nvs.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables  = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("total amount", amount);
        variables.put("orderReference", orderReference);
        variables.put("Products ", products);

        Context context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s",destinationEmail,templateName));
        }catch (MessagingException e){
            log.warn("WARNING - Cannot send email to {}",destinationEmail);
            e.getStackTrace();
        }
    }

}
