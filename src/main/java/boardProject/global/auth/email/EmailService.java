package boardProject.global.auth.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final EmailProperties emailProperties;



    public void sendMail(String receiver,String authCode)
            throws MessagingException, NoSuchAlgorithmException {

        javaMailSender.send(createVerificationMail(receiver,authCode));
    }

    public int getAuthCodeExpirationTime() {
        return emailProperties.getAuthCodeExpirationMinutes();
    }

    public int getMailRequestCoolTime() {
        return emailProperties.getMailRequestCooltime();
    }

    private MimeMessage createVerificationMail(String receiver, String authCode)
                                throws NoSuchAlgorithmException, MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();


        message.setFrom(emailProperties.getUsername());
        message.setRecipients(MimeMessage.RecipientType.TO,receiver);
        message.setSubject("BoardProject에 요청하신 인증코드 메일입니다");
        message.setText(createMessageBody(authCode),"UTF-8", "html");


        return message;
    }

    private String createMessageBody(String authCode) {

        return        "<h3>요청하신 인증 번호입니다.(5분간 유효합니다)</h3>" +
                      "<h1>" + "✨ " + authCode + " ✨" + "</h1>" +
                      "<h3>감사합니다.</h3>";
    }





}
