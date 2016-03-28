package pl.mariuszpawlowski.turismonaescocia.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by mario on 25/03/16.
 */
@Component
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailStatus sendPlainText(String to, String replyTo, InternetAddress from, String subject, String text) {
        return sendMail(to, subject, text, replyTo, from, false);
    }

    public EmailStatus sendHtml(String to, String replyTo, InternetAddress from, String subject, String htmlBody) {
        return sendMail(to, subject, htmlBody, replyTo, from, true);
    }

    private EmailStatus sendMail(String to, String subject, String text, String replyTo, InternetAddress from, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            javaMailSender.send(mail);
            LOGGER.info("Send email '{}' to: {}", subject, to);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", to, e.getMessage()));
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }

}
