package boardProject.domain.account.dto;

import boardProject.global.constant.Constants;
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

    //@Size (min = 2, max = 5)
    private String name ;

    //@Pattern(regexp = "010-(\\d{4})-(\\d{4})")
    private String phoneNum ;

    //@Size (min = 2, max = 8)
    private String nickname ;

    private String description ;

    private String email ;

    private String password;


    @JsonSetter("name")
    public void updateName(String name) {
        this.name = (name == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : name ;
    }

    @JsonSetter("phoneNum")
    public void updatePhoneNum(String phoneNum) {
        this.phoneNum = (phoneNum == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : phoneNum;
    }

    @JsonSetter("nickname")
    public void updateNickname(String nickname) {
        this.nickname = (nickname == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : nickname;
    }

    @JsonSetter("description")
    public void updateDescription(String description) {
        this.description  = (description == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : description;
    }

    @JsonSetter("email")
    public void updateEmail(String email) {
        this.email = (email == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : email;
    }

    @JsonSetter("password")
    public void updatePassword(String password) {
        this.password = (password == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : password;
    }

}
