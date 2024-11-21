package boardProject.global.security.jwt;

import boardProject.domain.member.entity.Member;
import boardProject.global.security.encryption.key.asymmetric.RsaKey;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;

    private final RsaKey rsaKey;


    public String generateAccessToken(
                                        Map<String,Object> claims,
                                        String subject,
                                        Date expiration) {


        log.info(AUTH,"Attempt to create AccessToken based on member information");
        PrivateKey privateKey = rsaKey.getPrivateKey();

        return Jwts.builder()
                            .id(UUID.randomUUID().toString())
                            .claims(claims)
                            .subject(subject)
                            .issuedAt(Calendar.getInstance().getTime())
                            .expiration(expiration)
                            .signWith(privateKey)
                   .compact();
    }


    public String generateRefreshToken(String subject, Date expiration) {

        log.info(AUTH,"Attempt to create RefreshToken based on member information");
        PrivateKey privateKey = rsaKey.getPrivateKey();


        return Jwts.builder()
                            .id(UUID.randomUUID().toString())
                            .subject(subject)
                            .issuedAt(Calendar.getInstance().getTime())
                            .expiration(expiration)
                            .signWith(privateKey)
                   .compact();
    }



    public String delegateAccessToken(Member member) {

        log.info(AUTH,"Attempt to set member information to create AccessToken");

        // AccessToken 생성 로직
        Map<String,Object> claims = new HashMap<>();
        claims.put("memberId",member.getId());
        claims.put("roles",member.getRoles());
        String subject = member.getEmail();
        Date expiration = setTokenExpirationDate(getAccessTokenExpirationMinute());

        String accessToken = generateAccessToken(claims,subject,expiration);

        log.info(AUTH,"Success to set member information to create AccessToken");

        return accessToken;

    }

    public String delegateRefreshToken(Member member) {

        log.info(AUTH,"Attempt to set member information to create RefreshToken");

        String subject = member.getEmail();
        Date expiration = setTokenExpirationDate(getRefreshTokenExpirationMinute());

        String refreshToken = generateRefreshToken(subject, expiration);

        log.info(AUTH,"Success to set member information to create RefreshToken");

        return refreshToken;
    }


    public Date setTokenExpirationDate (int expirationMinute) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,expirationMinute);

        return calendar.getTime();
    }


    public int getAccessTokenExpirationMinute () {
        return Integer.parseInt(jwtProperties.getAccessExp());
    }

    public int getRefreshTokenExpirationMinute () {
        return Integer.parseInt(jwtProperties.getRefreshExp());
    }

}
