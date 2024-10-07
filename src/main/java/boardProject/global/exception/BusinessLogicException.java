package boardProject.global.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException  {


    private StatusCode statusCode;

    public BusinessLogicException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }




}
