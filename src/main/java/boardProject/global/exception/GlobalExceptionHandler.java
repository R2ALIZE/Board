package boardProject.global.exception;

import boardProject.global.response.ErrorResponse;
import boardProject.global.response.Response;
import boardProject.global.util.slack.SlackUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.IOException;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SlackUtil slackUtil;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    @ExceptionHandler(BusinessLogicException.class)
    public Response<ErrorResponse> handleBusinessLogicException
            (BusinessLogicException be) throws IOException {

        log.error("handleBusinessLogicException : {}",be.getMessage());

        slackUtil.sendAlert(be,request);
        response.setHeader("Mvc Exception","true");

        return new Response<>(ErrorResponse.of(be.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException me) {

        log.warn("handleMethodArgumentNotValidException : {}", me.getMessage());
        response.setHeader("Mvc Exception","true");

        return new Response<>(ErrorResponse.of(me.getBindingResult()));

    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMethodValidationException (HandlerMethodValidationException he) {

        log.warn("handleMethodValidationException : {}", he.getMessage());
        response.setHeader("Mvc Exception","true");

        return new Response<>(ErrorResponse.of(he));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorResponse> handleAllException
            (Exception e) throws IOException {

        log.error("handleException : {}, cause : {}", e.getMessage(), e.getCause());

        slackUtil.sendAlert(e,request);
        response.setHeader("Mvc Exception","true");

        return new Response<>(ErrorResponse.of(e));

    }





}