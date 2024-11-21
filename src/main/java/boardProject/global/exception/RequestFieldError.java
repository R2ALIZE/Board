package boardProject.global.exception;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;


public record RequestFieldError(String field, String rejectedValue, String reason) {

    public static List<RequestFieldError> of(BindingResult bindingResult) {

        return bindingResult.getFieldErrors().stream()
                .map(error -> new RequestFieldError(
                        error.getField(),
                        error.getRejectedValue() == null ?
                                "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage())
                )
                .collect(Collectors.toList());
    }
}
