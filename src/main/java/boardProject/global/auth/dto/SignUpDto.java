package boardProject.global.auth.dto;

import boardProject.global.aop.format.ResidentNumFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotBlank
    @Size(min = 2, max = 5)
    private String name;

    @NotBlank
    @Size (min = 2, max = 8)
    private String nickname;

    // - 제외하고 13자리로 입력 받음
    @NotBlank
    @ResidentNumFormat
    @JsonProperty("residentNum")
    private String residentNum;

    @NotBlank
    @Pattern(regexp = "010-(\\d{4})-(\\d{4})")
    @JsonProperty("phoneNum")
    private String phoneNum;

    @NotBlank
    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size (min = 8, max = 12)
    private String password;

    @NotBlank
    private String gender;
}
