package com.peterson.helpdesk.event.listener;

import com.peterson.helpdesk.domain.Pessoa;
import com.peterson.helpdesk.event.OnUserRegistrationCompleteEvent;
import com.peterson.helpdesk.exceptions.MailSendException;
import com.peterson.helpdesk.security.UserSS;
import com.peterson.helpdesk.services.EmailVerificationTokenService;
import com.peterson.helpdesk.services.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j(topic = "ON_USER_REGISTRATION_COMPLETE_LISTENER")
@Component
public class OnUserRegistrationCompleteListener implements
        ApplicationListener<OnUserRegistrationCompleteEvent> {
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final MailService mailService;

    @Autowired
    public OnUserRegistrationCompleteListener(EmailVerificationTokenService emailVerificationTokenService, MailService mailService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     * asynchronously in an another thread pool
     */
    @Override
    @Async
    public void onApplicationEvent(OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent) {
        sendEmailVerification(onUserRegistrationCompleteEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendEmailVerification(OnUserRegistrationCompleteEvent event) {
        Pessoa user = event.getUser();
        String token = emailVerificationTokenService.generateNewToken();
        emailVerificationTokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();

        try {
            mailService.sendEmailVerification(token, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error(e.getMessage());
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
