package pl.Tiguarces.TGbook.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.Tiguarces.TGbook.security.exceptions.MailException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailSender {
    private String from;
    private String to;
    private String nickname;
    private String activatedToken;
    private String link;
    private final JavaMailSenderImpl sender;

    public MailSender() {
        sender = new JavaMailSenderImpl();
        sender.setPort(465);
        sender.setHost("smtp.mailtrap.io");
        sender.setUsername("0f1bf8c71d6205");
        sender.setPassword("03e0793237fc05");
    }

    @Async
    public void sendEmail(final String nickname, final String activationToken, final String from, final String to, final MailType mailType) {
        checkStrings(from, to, nickname, activationToken);
        send(mailType);
    }

    /**
     * Method which set class fields to new values and check if given parameters are null,
     * if so throws right exception, which will be handled by custom Exception Handler
     *
     * @param from              email parameter
     * @param to                email parameter
     * @param nickname          email parameter
     * @param activationToken   email parameter
     */
    private void checkStrings(final String from, final String to, String nickname, String activationToken) {
        if (from == null)
            throw new NullPointerException("Email parameter: from cannot be null");

        else if (to == null)
            throw new NullPointerException("Email parameter: to cannot be null");


        else if (nickname == null)
            throw new NullPointerException("Email parameter: nickname cannot be null");

        else if (activationToken == null)
            throw new NullPointerException("Email parameter: activation token cannot be null");

        this.to = to;
        this.from = from;
        this.nickname = nickname;
        this.activatedToken = activationToken;
    }

    private void send(final MailType type) {
        try {
            final MimeMessage message = sender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

            final String link = "http://localhost:8080/auth/activate?nickname=%s&token=%s";
            final String formattedLink = link.formatted(nickname, activatedToken);
            final String text = type.getMessage()
                                        .formatted(nickname, formattedLink);

            messageHelper.setTo(to);
            messageHelper.setFrom(from);
            messageHelper.setSubject(type.getSubject());
            messageHelper.setText(text, true);

            sender.send(message);
        } catch (final MessagingException exception) {
            log.error("Email has not been sent, exception was occurred: " + exception.getMessage());
            throw new MailException("Email has not been sent, server error");
        }
    }
}
