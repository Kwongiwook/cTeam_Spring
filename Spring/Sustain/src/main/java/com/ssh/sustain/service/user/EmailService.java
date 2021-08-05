package com.ssh.sustain.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Log4j2
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendEmail(String email) throws Exception {
        String code = generateCode();
        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Authentication Code");
        message.setText(setContext(code), "UTF-8", "html");
        mailSender.send(message);

        return code;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);

        // mail.html
        return templateEngine.process("mail", context);
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = random.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((random.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

}
