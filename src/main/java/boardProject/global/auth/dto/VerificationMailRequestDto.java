package boardProject.global.auth.dto;


import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// (Default) isVerify == false
public record VerificationMailRequestDto(@NotBlank @Email String email,
                                         @AssertFalse boolean isVerify) {}
