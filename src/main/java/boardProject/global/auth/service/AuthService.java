package boardProject.global.auth.service;

import boardProject.domain.member.entity.Member;
import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.auth.dto.*;
import boardProject.global.response.AuthResponse;
import boardProject.global.response.Response;
import boardProject.global.security.jwt.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import static boardProject.global.exception.StatusCode.*;
import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberServiceHelper memberServiceHelper;

    private final AuthServiceHelper authServiceHelper;

    private final JwtTokenService jwtTokenService;


    public Response<AuthResponse> register (SignUpDto signUpDto)
            throws GeneralSecurityException, UnsupportedEncodingException {


        Member applicantInfo =
                memberServiceHelper.convertToMember(signUpDto);

        Member applicant =
                memberServiceHelper.memberBuilder(applicantInfo);


        // DB 저장
        memberServiceHelper.saveMember(applicant);

        // 응답 생성
        AuthResponse response = AuthResponse.builder()
                                                        .memberId(applicant.getId())
                                                        .request("REGISTRATION")
                                            .build();

        return new Response<>(REGISTRATION_SUCCESS, response);

    }



    public Response<AuthResponse> reissueAccessToken(String refreshToken) {

        Claims claims = jwtTokenService.validateJwtToken(refreshToken);

        Long memberId = (Long) claims.get("memberId");
        Member requestingMember = memberServiceHelper.findSpecificMemberById(memberId);
        String newAccessToken = jwtTokenService.allocateAccessToken(requestingMember);

        var header = new HttpHeaders();
        header.set("Authorization","Bearer : " + newAccessToken);


        AuthResponse response = AuthResponse.builder()
                                                        .memberId(memberId)
                                                        .request("JWT REFRESH")
                                            .build();


        return new Response<>(JWT_REFRESH_SUCCESS,response);
    }


    public Response<AuthResponse> logout(String accessToken) {

        log.info(AUTH,"Attempt to Logout");
        jwtTokenService.addToBlacklist(jwtTokenService.removePrefixFromAccessToken(accessToken));
        log.info(AUTH,"Success to Logout");

        AuthResponse response = AuthResponse.builder()
                                                        .memberId(1L)
                                                        .request("LOGOUT")
                                            .build();

        return new Response<>(LOGOUT_SUCCESS,response);
    }



    public Response<AuthResponse> withdraw(Long memberId) {

        Member applicant = memberServiceHelper.findSpecificMemberById(memberId);
        memberServiceHelper.deleteMember(applicant);

        AuthResponse response = AuthResponse.builder()
                                                            .memberId(applicant.getId())
                                                            .request("WITHDRAW")
                                                .build();

        return new Response<>(WITHDRAWAL_SUCCESS,response);
    }


    public Response<AuthResponse> sendVerificationMail(VerificationMailRequestDto mailDto) 
                                                        throws MessagingException, NoSuchAlgorithmException {

        String receiver = mailDto.email();
        VerificationMailResponseDto responseDto = authServiceHelper.sendVerificationMail(receiver);


        AuthResponse<Object> response = AuthResponse.builder()
                                                                .request("SEND VERIFICATION MAIL")
                                                                .resultDetails(responseDto)
                                                    .build();

        return new Response<>(VERIFICATION_MAIL_SEND_SUCCESS,response);

    }

    public Response<AuthResponse> verifyAuthCode(AuthCodeRequestDto authCodeDto) {

        String applicantEmail = authCodeDto.email();
        String inputAuthCode = authCodeDto.authCode();


        AuthCodeResponseDto responseDto = authServiceHelper.verifyAuthCode(applicantEmail,inputAuthCode);

        AuthResponse<Object> response = AuthResponse.builder()
                                                        .request("AUTH CODE VERIFICATION")
                                                        .resultDetails(responseDto)
                                            .build();

        return new Response<>(VERIFICATION_CODE_MATCH,response);
    }

}



