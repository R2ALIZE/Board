package boardProject.domain.member.dto;


import boardProject.global.aop.ResidentNumFormat;
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
public class MemberSignUpDto {

    @NotNull
    @Size(min = 2, max = 5)
    private String name;

    @NotNull
    @Size (min = 2, max = 8)
    private String nickname;

    // - 제외하고 13자리로 입력 받음
    @NotNull
    @ResidentNumFormat
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
