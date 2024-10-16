package boardProject.domain.article.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PageInfo {

    private int currentPage;
    private int totalPage;

}
