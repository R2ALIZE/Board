package boardProject.domain.comment.response;

import boardProject.domain.comment.dto.CommentMultiResponseDto;
import boardProject.domain.comment.entity.Comment;
import boardProject.domain.comment.pagination.CommentPageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiCommentResponse {

    private List<CommentMultiResponseDto> commentInfoList;

    private CommentPageInfo commentPageInfo;


    public MultiCommentResponse(List<CommentMultiResponseDto> multiResponseDto, Page page) {
        this.commentInfoList = multiResponseDto;
        this.commentPageInfo = new CommentPageInfo(page.getNumber()+1, page.getTotalPages());
    }


    public static MultiCommentResponse of (List<CommentMultiResponseDto> multiResponseDto, Page<Comment> pagination) {
        return new MultiCommentResponse (multiResponseDto,pagination);
    }
}
