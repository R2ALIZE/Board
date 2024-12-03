package boardProject.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class AuthResponse<T> {

    private Long memberId;

    private String request;

    private T resultDetails;

}
