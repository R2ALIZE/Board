package boardProject.domain.comment.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentPageInfo {

    private int currentPage;
    private int totalPage;

}
