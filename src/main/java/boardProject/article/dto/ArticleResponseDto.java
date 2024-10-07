package boardProject.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponseDto {

    private String title;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


}
