package boardProject.global.auth.controller;

import boardProject.global.auth.dto.AuthCodeRequestDto;
import boardProject.global.auth.dto.SignUpDto;
import boardProject.global.auth.dto.VerificationMailRequestDto;
import boardProject.global.auth.service.AuthService;
import boardProject.global.response.AuthResponse;
import boardProject.global.response.Response;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping ("/registration")
    @ResponseStatus(CREATED)
    public Response<AuthResponse> signUp (@Valid @RequestBody SignUpDto signUpDto)
            throws GeneralSecurityException, UnsupportedEncodingException {
        return authService.register(signUpDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(OK)
    public Response<AuthResponse> reissue (@RequestHeader ("Refresh") String refreshToken) {
      return authService.reissueAccessToken(refreshToken);
    }

    @PostMapping("/logout")
    @ResponseStatus(NO_CONTENT)
    public Response<AuthResponse> logout (@RequestHeader ("Authorization") String accessToken) {
        return authService.logout(accessToken);
    }

    @PostMapping("/withdrawal/{member-id}")
    @ResponseStatus(NO_CONTENT)
    public Response<AuthResponse> withdrawal (@Positive @PathVariable("member-id") Long memberId) {
        return authService.withdraw(memberId);
    }

    @PostMapping("/email/verification-request")
    @ResponseStatus(OK)
    public Response<AuthResponse> sendVerificationMail (@Valid @RequestBody VerificationMailRequestDto mailDto)
            throws MessagingException, NoSuchAlgorithmException {
        return authService.sendVerificationMail(mailDto);
    }


    @PostMapping("/email/auth-code-verification")
    @ResponseStatus(OK)
    public Response<AuthResponse> verifyAuthCode (@Valid @RequestBody AuthCodeRequestDto authCodeDto)
            throws MessagingException, NoSuchAlgorithmException {
        return authService.verifyAuthCode(authCodeDto);
    }




}
