package boardProject.global.security.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter // 필드 바인딩을 위한 setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String accessExp;

    private String refreshExp;


}
