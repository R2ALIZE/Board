package boardProject.account.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSignUpDto {

    private String name;

    private String nickname;

    private String residentNum;

    private String phoneNum;

    private String address;

    private String email;

    private String password;
}
