package boardProject.global.exception;

import boardProject.global.response.Response;
import boardProject.util.SlackUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final SlackUtil slackUtil;

    @ExceptionHandler(BusinessLogicException.class)
    public Response<ErrorResponse> handleBusinessLogicException
            (BusinessLogicException be, HttpServletRequest req) throws IOException {

        log.warn("handleBusinessLogicException : {}",be.getMessage());

        return new Response<>(ErrorResponse.of(be.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException me, HttpServletRequest req) throws IOException {

        log.warn("handleMethodArgumentNotValidException : {}", me.getMessage());

        slackUtil.sendAlert(me,req);

        return new Response<>(ErrorResponse.of(me.getBindingResult()));

    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMethodValidationException (HandlerMethodValidationException he) {

        log.warn("handleMethodValidationException : {}", he.getMessage());

        return new Response<>(ErrorResponse.of(StatusCode.INVALID_REQUEST));
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorResponse> handleAllException
            (Exception e, HttpServletRequest req) throws IOException {

        log.warn("handleException : {}", e.getMessage());

        slackUtil.sendAlert(e,req);

        return new Response<>(ErrorResponse.of(e));

    }









}