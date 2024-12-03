package boardProject.global.redis.config;


import boardProject.global.redis.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Primary
    @Bean(name = "redisConnectionFactoryJwt")
    public LettuceConnectionFactory redisConnectionFactoryJWT() {
        return new LettuceConnectionFactory(getJwtRedisHost(),getJwtRedisPort());
    }

    @Bean(name = "redisConnectionFactoryMail")
    public LettuceConnectionFactory redisConnectionFactoryMail() {
        return new LettuceConnectionFactory(getMailRedisHost(),getMailRedisPort());
    }


    @Bean(name = "redisTemplateJwt")
    public RedisTemplate<String, Object> redisTemplateJWT() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactoryJWT());
        return redisTemplate;
    }

    @Bean(name = "redisTemplateMail")
    public RedisTemplate<String, Object> redisTemplateMail() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactoryMail());
        return redisTemplate;
    }






    private String getJwtRedisHost() {
        return redisProperties.getRedisJwt().getHost();
    }
    private int getJwtRedisPort() {
        return redisProperties.getRedisJwt().getPort();
    }
    private String getMailRedisHost() {
        return redisProperties.getRedisMail().getHost();
    }
    private int getMailRedisPort() {
        return redisProperties.getRedisMail().getPort();
    }





}
