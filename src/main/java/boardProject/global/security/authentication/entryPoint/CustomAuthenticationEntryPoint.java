package boardProject.global.security.authentication.entryPoint;

import boardProject.global.response.ErrorResponse;
import boardProject.global.exception.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;


@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /** 인증되지 않은 사용자가 인증(로그인)이 필요한 엔드포인트에 접근할 때 처리하는 클래스 **/

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        log.warn(AUTH,"Anonymous user not allowed, Please login");

        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(StatusCode.AUTHENTICATION_REQUIRED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(errorResponse,ErrorResponse.class));

    }
}
