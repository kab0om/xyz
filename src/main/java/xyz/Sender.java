package xyz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;

/**
 * Created by kab00m on 04.09.15.
 */
@Service
public class Sender {

    @Autowired
    private JavaMailSender mailSender;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void send(Collection lista) {
        log.debug("sending...");

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress("mariusz.ciok@gmail.com"));
                mimeMessage.setFrom(new InternetAddress("mail@mycompany.com"));
                mimeMessage.setText("Testowa wiadomość ");
            }
        };

        try{
            this.mailSender.send(preparator);
            log.debug("message sent...");
        }
        catch (MailException ex) {
            // simply log it and go on...
            log.error(ex.getMessage());
        }
    }
}
