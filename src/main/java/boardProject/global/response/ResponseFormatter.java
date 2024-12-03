package boardProject.global.response;

import boardProject.global.exception.StatusCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ResponseFormatter {


    public static void sendResponse(StatusCode statusCode, HttpServletResponse response) throws IOException {


        //Http response body
        ErrorResponse errorResponse = ErrorResponse.of(statusCode);


        //Http Response 뼈대 구축

        Gson gson = new Gson();
        response.setStatus(statusCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(errorResponse,ErrorResponse.class));
    }
}
