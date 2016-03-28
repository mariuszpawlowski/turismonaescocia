package pl.mariuszpawlowski.turismonaescocia.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;

/**
 * Created by mario on 25/03/16.
 */
@Component
public class EmailHtmlSender {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public EmailStatus send(String to, String replyTo, InternetAddress from, String subject, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        return emailSender.sendHtml(to, replyTo, from, subject, body);
    }
}
