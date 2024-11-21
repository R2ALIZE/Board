package boardProject.global.security.authorization.handler;

import boardProject.global.response.ErrorResponse;
import boardProject.global.exception.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    /** 인증이 완료된 사용자 (로그인한 사용자)가 다른 권한이 필요한 엔드포인트에 접근할 때 처리하는 클래스**/

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        log.warn(AUTH,"No authority to access",accessDeniedException);
        log.warn(AUTH,"Requested URI : {}",request.getRequestURI());


        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(StatusCode.INAPPROPRIATE_AUTHORITY);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(errorResponse,ErrorResponse.class));

    }
}
