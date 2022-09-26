package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.util.ConstUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MailContentService {
    private final TemplateEngine templateEngine;

    public String buildContent(String content) {
        Context context = new Context();
        context.setVariable("currentYear", LocalDateTime.now().getYear());
        context.setVariable("confirm", content);
        context.setVariable("host", ConstUtil.HOST_URL);
        return templateEngine.process("mail/mailTemplate", context);
    }

    public String buildContentResetPassword(String password) {
        Context context = new Context();
        context.setVariable("currentYear", LocalDateTime.now().getYear());
        context.setVariable("pass", password);
        context.setVariable("host", ConstUtil.HOST_URL);
        return templateEngine.process("mail/resetPassword", context);
    }
}
