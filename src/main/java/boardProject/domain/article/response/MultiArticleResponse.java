package boardProject.domain.article.response;

import boardProject.domain.article.dto.ArticleMultiResponseDto;
import boardProject.domain.article.entity.Article;
import boardProject.domain.article.pagination.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiArticleResponse {

    private List<ArticleMultiResponseDto> articleInfoList;

    private PageInfo pageInfo;


    public MultiArticleResponse(List<ArticleMultiResponseDto> multiResponseDto, Page page) {
        this.articleInfoList = multiResponseDto;
        this.pageInfo = new PageInfo(page.getNumber()+1, page.getTotalPages());
    }


    public static MultiArticleResponse of (List<ArticleMultiResponseDto> multiResponseDto, Page<Article> pagination) {
        return new MultiArticleResponse(multiResponseDto,pagination);
    }
}
