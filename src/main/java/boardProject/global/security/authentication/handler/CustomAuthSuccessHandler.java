package boardProject.global.security.authentication.handler;

import boardProject.domain.member.entity.Member;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.ResponseFormatter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {


      Member loggedMember = (Member) authentication.getPrincipal();

      ResponseFormatter.sendResponse(StatusCode.LOGIN_SUCCESS,response);

      log.info(AUTH,"Login Success!");
    }


}
