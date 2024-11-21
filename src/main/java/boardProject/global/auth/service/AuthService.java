package boardProject.global.auth.service;

import boardProject.domain.member.entity.Member;
import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.auth.dto.SignUpDto;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.AuthResponse;
import boardProject.global.response.Response;
import boardProject.global.security.jwt.JwtTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberServiceHelper memberServiceHelper;

    private final JwtTokenService jwtTokenService;



    public Response<AuthResponse> register (SignUpDto signUpDto)
            throws GeneralSecurityException, UnsupportedEncodingException {

        Member applicantInfo =
                memberServiceHelper.convertToMember(signUpDto);

        Member applicant =
                memberServiceHelper.memberBuilder(applicantInfo);

        memberServiceHelper.saveMember(applicant);


        AuthResponse response = AuthResponse.builder()
                                                        .memberId(applicant.getId())
                                                        .request("REGISTRATION")
                                            .build();

        return new Response<>(StatusCode.REGISTRATION_SUCCESS, response);

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


        return new Response<>(StatusCode.JWT_REFRESH_SUCCESS,response);
    }


    public Response<AuthResponse> logout(String accessToken) {

        log.info(AUTH,"Attempt to Logout");
        jwtTokenService.addToBlacklist(jwtTokenService.removePrefixFromAccessToken(accessToken));
        log.info(AUTH,"Success to Logout");

        AuthResponse response = AuthResponse.builder()
                                                        .memberId(1L)
                                                        .request("LOGOUT")
                                            .build();

        return new Response<>(StatusCode.LOGOUT_SUCCESS,response);
    }



    public Response<AuthResponse> withdraw(Long memberId) {

        Member applicant = memberServiceHelper.findSpecificMemberById(memberId);
        memberServiceHelper.deleteMember(applicant);

        AuthResponse response = AuthResponse.builder()
                                                            .memberId(applicant.getId())
                                                            .request("WITHDRAW")
                                                .build();

        return new Response<>(StatusCode.WITHDRAWAL_SUCCESS,response);
    }
}



