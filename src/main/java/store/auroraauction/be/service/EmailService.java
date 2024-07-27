package store.auroraauction.be.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import store.auroraauction.be.Models.EmailDetail;
import store.auroraauction.be.entity.Account;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMailTemplate(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("button", emailDetail.getButtonValue());
            context.setVariable("name", emailDetail.getFullName());

            String text = templateEngine.process("emailtemplate", context);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            // Setting up necessary details
            mimeMessageHelper.setFrom("hung44570@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }

    public void sendMailTemplate2(EmailDetail emailDetail){
        try{
            // Create the email context
            Context context = new Context();
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("button", emailDetail.getButtonValue());
            context.setVariable("name", emailDetail.getFullName());
            // Process the template to create eamil content
            String text = templateEngine.process("emailsuccessful", context);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            // Setting up necessary details
            mimeMessageHelper.setFrom("hung44570@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            // Send the email
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }
    public void sendMailFail(EmailDetail emailDetail){
        try{
            Context context = new Context();
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("button", emailDetail.getButtonValue());
            context.setVariable("name", emailDetail.getFullName());

            String text = templateEngine.process("fail", context);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            // Setting up necessary details
            mimeMessageHelper.setFrom("hung44570@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }

    public void sendMail(Account account, String subject, String description){

        try{
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            // Setting up necessary details
            mimeMessageHelper.setFrom("admin@gmail.com");
            mimeMessageHelper.setTo(account.getEmail());
            mimeMessageHelper.setText(description);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }
}
