package boardProject.account.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePostDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣\\p{Punct}\\s]+$")
    @Size(min = 5, max = 50, message = "제목은 5자 이상 50자 이하로 작성되어야 합니다." )

    private String title;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣\\p{Punct}\\s]+$")
    @Size(min = 5, max = 1000, message = "본문은 5자 이상 1000자 이하로 작성되어야 합니다." )
    private String body;


}
