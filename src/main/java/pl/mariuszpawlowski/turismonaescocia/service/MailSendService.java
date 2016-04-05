package pl.mariuszpawlowski.turismonaescocia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import pl.mariuszpawlowski.turismonaescocia.component.EmailHtmlSender;
import pl.mariuszpawlowski.turismonaescocia.component.EmailStatus;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

/**
 * Created by mariusz.pawlowski on 2016-03-25.
 */
@Service
public class MailSendService {

    @Autowired
    EmailHtmlSender emailHtmlSender;

    @Value("${contact.form.sendTo}")
    private String sendTo;

    @Value("${contact.form.sendFromName}")
    private String sendFromName;

    @Value("${contact.form.title}")
    private String emailTitle;

    public EmailStatus sendContactForm(String name, String email, String message) throws UnsupportedEncodingException {

        InternetAddress from = new InternetAddress();
        from.setAddress(sendTo);
        from.setPersonal(sendFromName);

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("message", message);

        EmailStatus emailStatus = emailHtmlSender.send(sendTo, email, from, emailTitle, "contactForm", context);

        return emailStatus;

    }
}
