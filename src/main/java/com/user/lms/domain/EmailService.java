package com.user.lms.domain;

import com.user.lms.entity.User;
import com.user.lms.models.UserDetailsModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    public void sendResetPasswordEmail(String email,String token){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            context.setVariable("token", token);


            String htmlContent = templateEngine.process("reset-password-template", context);

            helper.setTo(email);
            helper.setSubject("Reset Password");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
        }
    }
    public void sendRegistrationEmail(User user){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            UserDetailsModel userDetailsModel = UserDetailsModel.fromEntity(user);
            context.setVariable("user", userDetailsModel);


            String htmlContent = templateEngine.process("email-verification", context);

            helper.setTo(userDetailsModel.getEmail());
            helper.setSubject("Email Verification");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
        }
    }
}
