package boardProject.global.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    // Getters and Setters
    private RedisServer redisJwt;
    private RedisServer redisMail;


    @Getter
    @Setter
    public static class RedisServer {

        private String host;
        private int port;
        private String password;

    }
}
