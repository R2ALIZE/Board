package boardProject.global.exception;

import boardProject.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public Response<ErrorResponse> handleBusinessLogicException (BusinessLogicException be) {

        log.warn("handleBusinessLogicException : {}",be.getMessage());

        return new Response<>(ErrorResponse.of(be.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {

        log.warn("handleMethodArgumentNotValidException : {}", me.getMessage());

        return new Response<>(ErrorResponse.of(me.getBindingResult()));

    }








}