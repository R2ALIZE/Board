package boardProject.global.response;


import boardProject.global.exception.ErrorResponse;
import boardProject.global.exception.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T>{

    private StatusCode statusCode;

    private String requestResult;

    private T resultDetails;



    public Response(T resultDetails) {
        this.requestResult = SuccessOrFail(resultDetails);
        this.resultDetails = resultDetails;
    }


    public Response(StatusCode statusCode,  T resultDetails) {
        this.statusCode = statusCode;
        this.requestResult = SuccessOrFail(resultDetails);
        this.resultDetails = resultDetails;
    }


    public String SuccessOrFail (T t) {

        if (t instanceof ErrorResponse) {
            return "Fail";
        } else {
            return "Success";
        }

    }


}
