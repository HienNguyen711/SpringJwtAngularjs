package org.spring.jwt.angularjs.listener;

import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.event.OnRegistrationCompleteEvent;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
			this.confirmRegistration(event);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws ServiceException {
    	
        User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        
        // create token
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = messageSource.getMessage("title.regist.confirm", null, Locale.US);
        String confirmationUrl = event.getAppUrl() + URIConstant.REGIST_ACTIVE + token;
        String message = messageSource.getMessage("message.regist.success", null, Locale.US);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \n" + confirmationUrl);
        logger.info("registration email: " + email.getText());

        try {
            mailSender.send(email);
        } catch (Exception e) {
            logger.error("Error sending mail: " + e.getMessage());
        }
    }
}
