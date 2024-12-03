package boardProject.global.redis.util;

import boardProject.global.auth.email.AuthCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String,Object> redisTemplateJwt;

    private final RedisTemplate<String,Object> redisTemplateMail;


    public RedisService(@Qualifier("redisTemplateJwt") RedisTemplate<String, Object> redisTemplateJwt,
                        @Qualifier("redisTemplateMail") RedisTemplate<String, Object> redisTemplateMail) {
        this.redisTemplateJwt = redisTemplateJwt;
        this.redisTemplateMail = redisTemplateMail;
    }


    /** 메일 인증코드 REDIS SERVER **/

    public void addAuthCode(String key, Object o, int minutes) {
        redisTemplateMail.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisTemplateMail.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public AuthCode getAuthCode(String key) {
        redisTemplateMail.setValueSerializer(new Jackson2JsonRedisSerializer<>(AuthCode.class));
        return (AuthCode) redisTemplateMail.opsForValue().get(key);
    }

    public void deleteAuthCode(String key) {
        redisTemplateMail.delete(key);
    }

    public boolean hasAuthCode(String key) {
        return Boolean.TRUE.equals(redisTemplateMail.hasKey(key));
    }



    /** JWT BLACKLIST REDIS SERVER **/

    public void addBlackList(String key, Object o, Long seconds) {
        redisTemplateJwt.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisTemplateJwt.opsForValue().set(key, o, seconds, TimeUnit.SECONDS);
    }

    public String getBlackList(String key) {
        redisTemplateJwt.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return (String) redisTemplateJwt.opsForValue().get(key);
    }

    public void deleteBlackList(String key) {
        redisTemplateJwt.delete(key);
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplateJwt.hasKey(key));
    }

}
