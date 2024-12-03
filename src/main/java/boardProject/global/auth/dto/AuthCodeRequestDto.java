package boardProject.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthCodeRequestDto(@NotBlank @Email String email,

                                 @NotBlank @Pattern(regexp = "^\\d{6}$")
                                 @JsonProperty("authCode") String authCode) {}
