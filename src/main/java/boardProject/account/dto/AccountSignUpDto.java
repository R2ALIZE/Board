package boardProject.account.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSignUpDto {

    @NotNull
    @Size(min = 2, max = 5)
    private String name;

    @NotNull
    @Size (min = 2, max = 8)
    private String nickname;

    @NotNull
    @Pattern(regexp = "^(\\d{6})-(\\d{7})$")
    private String residentNum;

    @NotNull
    @Pattern(regexp = "010-(\\d{4})-(\\d{4})")
    private String phoneNum;

    @NotNull
    private String address;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size (min = 8, max = 12)
    private String password;
}
