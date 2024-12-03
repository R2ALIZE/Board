package boardProject.global.auth.service;

import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.auth.dto.AuthCodeResponseDto;
import boardProject.global.auth.dto.VerificationMailResponseDto;
import boardProject.global.auth.email.AuthCode;
import boardProject.global.auth.email.EmailService;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.redis.util.RedisService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;

import static boardProject.global.exception.StatusCode.*;

@Component
@RequiredArgsConstructor
public class AuthServiceHelper {

    private final MemberServiceHelper memberServiceHelper;

    private final RedisService redisService;

    private final EmailService emailService;


    public VerificationMailResponseDto sendVerificationMail (String receiver) throws NoSuchAlgorithmException, MessagingException {

        preProcessByAuthCodeCondition(receiver);

        int authCodeExpirationTime = emailService.getAuthCodeExpirationTime();
        long requestTime = System.currentTimeMillis();
        AuthCode authCode = new AuthCode(createVerificationCode(),requestTime);

        // 인증코드 메일 유효기간 설정 (5분)
        redisService.addAuthCode(receiver,authCode,authCodeExpirationTime);

        emailService.sendMail(receiver,authCode.authCode());

        return new VerificationMailResponseDto(receiver,true);

    }


    public AuthCodeResponseDto verifyAuthCode (String applicantEmail, String inputAuthCode) {

        memberServiceHelper.checkDuplicateMember(applicantEmail);

        checkInputAuthCodeValidity(applicantEmail);

        checkInputAuthCodeMatching(applicantEmail,inputAuthCode);

        // 인증 성공 후 남아있는 인증코드 삭제
        redisService.deleteAuthCode(applicantEmail);

        return new AuthCodeResponseDto(applicantEmail,true);
    }


    private void preProcessByAuthCodeCondition(String receiver) {

        if(isFirstTimeRequest(receiver)) {
            memberServiceHelper.checkDuplicateMember(receiver);
        } else {
            checkVerificationMailRequestCoolTime(receiver);
            invalidateOldAuthCode(receiver);
        }

    }

    private String createVerificationCode() throws NoSuchAlgorithmException {

        final int CODE_LENGTH = 6;

        Random random = SecureRandom.getInstanceStrong();

        return random.ints(CODE_LENGTH, 0, 10)
                     .mapToObj(Integer::toString)
                     .collect(Collectors.joining());
    }


    private void checkInputAuthCodeValidity(String applicantEmail) {

        if (!redisService.hasAuthCode(applicantEmail)) {
            throw new BusinessLogicException(VERIFICATION_CODE_TIMEOUT);
        }
    }

    private void checkInputAuthCodeMatching(String applicantEmail, String inputAuthCode) {

        String authCodeInRedis = redisService.getAuthCode(applicantEmail).authCode();

        if (!authCodeInRedis.equals(inputAuthCode)) {
            throw new BusinessLogicException(VERIFICATION_CODE_MISMATCH);
        }
    }

    private void invalidateOldAuthCode (String receiver) {
        redisService.deleteAuthCode(receiver);
    }

    private void checkVerificationMailRequestCoolTime (String applicant) {

        // 재요청 쿨타임 1분
        final long MAIL_REQUEST_COOLTIME_MINUTE = emailService.getMailRequestCoolTime();

        long lastRequestTime = redisService.getAuthCode(applicant).lastRequestTime();
        long currentTime = System.currentTimeMillis();

        // 1분 안에 인증 메일을 재요청하는 경우
        if (currentTime - lastRequestTime < MAIL_REQUEST_COOLTIME_MINUTE * 60 * 1000) {
            throw new BusinessLogicException(MAIL_REQUEST_COOLDOWN);
        }

    }

    private boolean isFirstTimeRequest(String receiver) {
        return !redisService.hasAuthCode(receiver);
    }
}
