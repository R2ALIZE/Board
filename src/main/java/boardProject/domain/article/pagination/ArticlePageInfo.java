package boardProject.domain.article.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticlePageInfo {

    private int currentPage;
    private int totalPage;

}
