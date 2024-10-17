package boardProject.domain.comment.response;

import boardProject.domain.comment.dto.CommentResponseDto;
import lombok.Getter;

@Getter
public class SingleCommentResponse {

    CommentResponseDto commentInfo;

    public SingleCommentResponse(CommentResponseDto ResponseDto) {
        this.commentInfo = ResponseDto;
    }
    public static SingleCommentResponse of (CommentResponseDto responseDto ) {
        return new SingleCommentResponse(responseDto);
    }

}
