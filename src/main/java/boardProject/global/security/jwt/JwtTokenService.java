package boardProject.global.security.jwt;

import boardProject.domain.member.entity.Member;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.global.redis.util.RedisService;
import boardProject.global.security.encryption.key.asymmetric.RsaKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenService {


    private final JwtTokenGenerator jwtTokenGenerator;

    private final RedisService redisService;

    private final RsaKey rsaKey;


    public String allocateAccessToken (Member member) {
        return jwtTokenGenerator.delegateAccessToken(member);
    }

    public String allocateRefreshToken (Member member) {
        return jwtTokenGenerator.delegateRefreshToken(member);
    }


    public void allocateJwtToResponseHeader (Member authenticatedMember, HttpServletResponse response) {

        String accessToken = allocateAccessToken(authenticatedMember);
        String refreshToken = allocateRefreshToken(authenticatedMember);

        response.setHeader("Authorization", "Bearer : " + accessToken);
        response.setHeader("Refresh", refreshToken);
    }



    public Claims validateJwtToken (String jwt) {

        PrivateKey privateKey = rsaKey.getPrivateKey();

        try {
            Claims claims = Jwts.parser()
                                         .decryptWith(privateKey)
                                .build()
                                         .parseSignedClaims(jwt)
                                         .getPayload();


            if (isTokenBlacklisted(claims.getId())) {
                throw new BusinessLogicException(StatusCode.TOKEN_BLACKLISTED);
            }

            return claims;
        } catch (SignatureException se) {
            throw new BusinessLogicException(StatusCode.INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException ee) {
            throw new BusinessLogicException(StatusCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException ie) {
            throw new BusinessLogicException(StatusCode.TOKEN_NOT_FOUND);
        } catch (MalformedJwtException me) {
            throw new BusinessLogicException(StatusCode.INVALID_TOKEN_FORMAT);
        }
    }


    public Date getTokenExpirationDate (String jwt) {

        Claims claims = validateJwtToken(jwt);

        return claims.getExpiration();
    }


    public Long getMemberIdFromToken (String jwt) {
        return (Long) validateJwtToken(jwt).get("memberId");
    }

    public String extractAccessToken (HttpServletRequest req) {

        String token = Optional.ofNullable((req.getHeader("Authorization")))
                               .orElseThrow(
                                       () -> new BusinessLogicException(StatusCode.HEADER_NOT_FOUND)
                               );

        if (!token.startsWith("Bearer : ")) {
            throw new BusinessLogicException(StatusCode.INVALID_TOKEN_FORMAT);
        }

         return removePrefixFromAccessToken(token);

    }

    public String removePrefixFromAccessToken(String token) {
        return token.replace("Bearer : ", "");
    }


    public String extractRefreshToken (HttpServletRequest req) {

        return Optional.ofNullable(req.getHeader("Refresh"))
                                      .orElseThrow(
                                              () -> new BusinessLogicException(StatusCode.HEADER_NOT_FOUND)
                                      );
    }


    public void addToBlacklist(String token) {

        log.info(AUTH,"Attempt to add requested accessToken to Redis Blacklist");

        String jti = validateJwtToken(token).getId();


        LocalDateTime expirationDate = validateJwtToken(token).getExpiration()
                                                              .toInstant()
                                                              .atZone(ZoneId.systemDefault())
                                                              .toLocalDateTime();


        LocalDateTime now = LocalDateTime.now();

        long ttlInSeconds = Duration.between(now,expirationDate).getSeconds();

        redisService.addBlackList(jti, "blacklisted", ttlInSeconds);

        log.info(AUTH,"Success to add requested refreshToken to Redis Blacklist");
    }


    public boolean isTokenBlacklisted(String jti) {
        return redisService.hasKeyBlackList(jti);
    }


    public void removeFromBlacklist(String token) {
        redisService.deleteBlackList(token);
    }


}
