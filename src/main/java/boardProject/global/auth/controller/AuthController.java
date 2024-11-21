package boardProject.global.auth.controller;

import boardProject.global.auth.dto.SignUpDto;
import boardProject.global.auth.service.AuthService;
import boardProject.global.response.AuthResponse;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping ("/auth/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<AuthResponse> signUp (@Valid @RequestBody SignUpDto signUpDto)
            throws GeneralSecurityException, UnsupportedEncodingException {
        return authService.register(signUpDto);
    }

    @PostMapping("/auth/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Response<AuthResponse> reissue (@RequestHeader ("Refresh") String refreshToken) {
      return authService.reissueAccessToken(refreshToken);
    }

    @PostMapping("/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<AuthResponse> logout (@RequestHeader ("Authorization") String accessToken) {
        return authService.logout(accessToken);
    }

    @PostMapping("/auth/withdrawal/{member-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<AuthResponse> withdrawal (@Positive @PathVariable("member-id") Long memberId) {
        return authService.withdraw(memberId);
    }



}
