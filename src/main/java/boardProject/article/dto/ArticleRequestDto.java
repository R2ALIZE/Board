package boardProject.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {

    @NotBlank (message = "제목은 5자 이상 50자 이내여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\};:'\",.<>\\/?~`|\\\\]{5,50}$",
            message = "제목은 한글,영어,특수문자 조합으로 5자 이상, 50자 이내로 작성해야 합니다.")
    private String title;


    @NotBlank (message = "본문은 5자 이상 1000자 이내여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\};:'\",.<>\\/?~`|\\\\]{5,1000}$",
             message = "본문은 한글,영어,특수문자 조합으로 5자 이상, 1000자 이내로 작성해야 합니다.")
    private String body;


}
