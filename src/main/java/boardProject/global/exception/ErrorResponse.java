package boardProject.global.exception;

import boardProject.global.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;



/* exception 발생 시에 exception 정보를 response 제공하기 위한 클래스*/

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String timeStamp = TimeUtil.getNowAsUtcZero();

    private Integer status;

    private String divisionCode;

    private String resultMessage;

    private List<RequestFieldError> validationErrors;


    ErrorResponse(final StatusCode code) {
        this.status = code.getStatus();
        this.divisionCode = code.getDivisionCode();
        this.resultMessage = code.getMessage();
    }

    ErrorResponse(final Exception e) {
        this.status = 500;
        this.divisionCode = "E999";
        this.resultMessage = e.getMessage();
    }

    ErrorResponse (List<RequestFieldError> fieldErrors ) {
        this.validationErrors = fieldErrors;
    }

    public static ErrorResponse of (StatusCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of (BindingResult bindingResult) {
        return new ErrorResponse(RequestFieldError.of(bindingResult));
    }
    public static ErrorResponse of (Exception e) {
        return new ErrorResponse(e);
    }















}
