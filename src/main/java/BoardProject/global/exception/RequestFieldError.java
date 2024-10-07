package BoardProject.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class RequestFieldError {

    private String field;

    private String rejectedValue;

    private String reason;


    public RequestFieldError(String field, String rejectedValue, String reason) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.reason = reason;
    }

    public static List<RequestFieldError> of (BindingResult bindingResult) {

      return  bindingResult.getFieldErrors().stream()
                                      .map(error -> new RequestFieldError(
                                              error.getField(),
                                              error.getRejectedValue() == null ?
                                                      "" : error.getRejectedValue().toString(),
                                              error.getDefaultMessage()
                                              )
                                      )
                                      .collect(Collectors.toList());

    }
}
