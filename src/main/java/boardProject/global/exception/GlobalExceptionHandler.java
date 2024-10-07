package boardProject.global.exception;

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
    public ErrorResponse handleBusinessLogicException (BusinessLogicException be) {

        log.warn("handleBusinessLogicException : {}",be.getMessage());

        return ErrorResponse.of(be.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {

        log.warn("handleMethodArgumentNotValidException : {}", me.getMessage());

        return ErrorResponse.of(me.getBindingResult());

    }








}