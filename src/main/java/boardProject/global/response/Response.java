package boardProject.global.response;


import boardProject.global.exception.StatusCode;
import boardProject.global.util.time.TimeUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T>{

    private final String timeStamp = TimeUtil.getNowAsUtcZero();

    private StatusCode statusCode;

    private final String requestResult;

    private final T resultDetails;



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
