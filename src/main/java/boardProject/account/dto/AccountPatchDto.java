package boardProject.account.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //Jackson의 private 필드에 대한 접근 허용
public class AccountPatchDto {

    // patch request body에 명시적으로 null이 선언 될 때 (데이터를 null로 바꾸고 싶을 때) 필드 누락과 구별하기 위한 문자열
    @JsonIgnore
    private final String EXPRESSION_OF_EXPLICIT_NULL = "blank";


    @Size (min = 2, max = 5)
    private String name ;


    @Pattern(regexp = "010-(\\d{4})-(\\d{4})")
    private String phoneNum ;


    @Size (min = 2, max = 8)
    private String nickname ;

    private String description ;

    private String email ;

    private String password;


    @JsonSetter
    public void updateName(String name) {
        this.name = (name == null) ? EXPRESSION_OF_EXPLICIT_NULL : name ;
    }

    @JsonSetter
    public void updatePhoneNum(String phoneNum) {
        this.phoneNum = (phoneNum == null) ? EXPRESSION_OF_EXPLICIT_NULL : phoneNum;
    }

    @JsonSetter
    public void updateNickname(String nickname) {
        this.nickname = (nickname == null) ? EXPRESSION_OF_EXPLICIT_NULL : nickname;
    }

    @JsonSetter
    public void updateDescription(String description) {
        this.description  = (description == null) ? EXPRESSION_OF_EXPLICIT_NULL : description;
    }

    @JsonSetter
    public void updateEmail(String email) {
        this.email = (email == null) ? EXPRESSION_OF_EXPLICIT_NULL : email;
    }

    @JsonSetter
    public void updatePassword(String password) {
        this.password = (password == null) ? EXPRESSION_OF_EXPLICIT_NULL : password;
    }

}
