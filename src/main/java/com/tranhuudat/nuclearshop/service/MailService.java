package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.NotificationEmail;
import com.tranhuudat.nuclearshop.exception.NuclearShopException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@EnableAsync
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentService mailContentService;
    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom("nuclearshop123@gmail.com");
            messageHelper.setTo(notificationEmail.getEmail());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentService.buildContent(notificationEmail.getLink()),true);
            ClassPathResource logo = new ClassPathResource("/static/logo/logo.jpg");
            ClassPathResource login = new ClassPathResource("/static/logo/login.png");
            messageHelper.addInline("logo",logo);
            messageHelper.addInline("login",login);
        };
        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail because of exception {}",e.getMessage());
            throw new NuclearShopException("Exception occurred when sending mail to " + notificationEmail.getEmail(), e);
        }
    }
}
