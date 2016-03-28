package pl.mariuszpawlowski.turismonaescocia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mariuszpawlowski.turismonaescocia.component.EmailStatus;
import pl.mariuszpawlowski.turismonaescocia.model.ContactForm;
import pl.mariuszpawlowski.turismonaescocia.service.MailSendService;

import java.io.UnsupportedEncodingException;

/**
 * Created by mario on 24/01/16.
 */
@Controller
public class HomeController {

    @Autowired
    private MailSendService mailSendService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    String contact(Model model){
        model.addAttribute("contactForm", new ContactForm());
        return "index";
    }

    @RequestMapping(value="/contact", method= RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute ContactForm contactForm) {
        String resultTemplate;
        EmailStatus status = null;

        try {
            status = mailSendService.sendContactForm(contactForm.getName(), contactForm.getPhone(), contactForm.getEmail(), contactForm.getMessage());
        } catch (UnsupportedEncodingException e) {
            resultTemplate = "index_email_failed";
        }

        if (status != null && status.isSuccess()) {
            resultTemplate = "index_email_ok";
        } else {
            resultTemplate = "index_email_failed";
        }


        return resultTemplate;
    }
}
