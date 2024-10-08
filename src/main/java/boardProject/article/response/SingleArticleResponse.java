package boardProject.article.response;

import boardProject.article.dto.ArticleResponseDto;
import lombok.Getter;

@Getter
public class SingleArticleResponse {

    ArticleResponseDto articleInfo;


    public SingleArticleResponse(ArticleResponseDto articleResponseDto) {
        this.articleInfo = articleResponseDto;
    }

    public static SingleArticleResponse of (ArticleResponseDto articleResponseDto) {
        return new SingleArticleResponse(articleResponseDto);
    }

}
