package boardProject.domain.article.dto;


import boardProject.global.constant.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ArticlePatchDto {


    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣\\p{Punct}\\s]+$",
            message = "제목은 한글,영어,특수문자 조합으로 5자 이상, 50자 이내로 작성해야 합니다.")
    @Size(min = 5, max = 50)
    private String title;


    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣\\p{Punct}\\s]+$",
            message = "본문은 한글,영어,특수문자 조합으로 5자 이상, 1000자 이내로 작성해야 합니다.")
    @Size(min = 5, max = 1000)
    private String body;



    @JsonSetter("title")
    public void updateTitle(String title) {
        this.title = (title == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : title;
    }

    @JsonSetter("body")
    public void updateBody(String body) {
        this.body = (body == null) ? Constants.EXPRESSION_OF_EXPLICIT_NULL : body;
    }
}
