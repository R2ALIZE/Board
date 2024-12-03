package boardProject.global.auth.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.mail")
public class EmailProperties {

    private String host;

    private int port;

    // 인증메일 발신인
    private String username;

    // GOOGLE 앱 비밀번호
    private String password;

    private int authCodeExpirationMinutes;

    private int mailRequestCooltime;

    private Properties properties;


    @Getter
    @Setter
    public static class Properties {
        private boolean auth;
        private boolean starttlsEnable;
        private boolean starttlsRequired;
    }



}
