package boardProject.article.response;

import boardProject.article.dto.ArticleMultiResponseDto;
import boardProject.article.entity.Article;
import boardProject.article.pagination.PageInfo;
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
