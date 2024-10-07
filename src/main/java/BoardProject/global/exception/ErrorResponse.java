package BoardProject.global.exception;

import BoardProject.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;



/* exception 발생 시에 exception 정보를 response 제공하기 위한 클래스*/

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String timeStamp = TimeUtil.getNowAsUtcZero() + " (UTC+0)";

    private Integer status;

    private String divisionCode;

    private String resultMessage;

    private List<RequestFieldError> validationErrors;


    ErrorResponse(final StatusCode code) {
        this.status = code.getHttpStatus();
        this.divisionCode = code.getDivisionCode();
        this.resultMessage = code.getMessage();
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






}
