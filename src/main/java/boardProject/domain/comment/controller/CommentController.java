package boardProject.domain.comment.controller;

import boardProject.domain.comment.dto.CommentPatchDto;
import boardProject.domain.comment.dto.CommentPostDto;
import boardProject.domain.comment.response.MultiCommentResponse;
import boardProject.domain.comment.response.SingleCommentResponse;
import boardProject.domain.comment.service.CommentService;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 단일 조회
    @GetMapping ("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<SingleCommentResponse> getComment
    (@Positive @PathVariable(name = "comment-id") Long commentId) {
        return commentService.findComment(commentId);
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<MultiCommentResponse> getComments
    (@Positive @RequestParam (value = "page", defaultValue = "0") int page,
     @Positive @RequestParam (value = "size", defaultValue = "10") int size) {
        return commentService.findComments(page,size);
    }

    // 아티클 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> postComment (@RequestBody @Valid CommentPostDto commentPostDto) {
        return commentService.createComment(commentPostDto);
    }

    // 아티클 수정
    @PatchMapping ("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> patchComment (@Positive @PathVariable ("comment-id") Long commentId,
                                        @RequestBody @Valid CommentPatchDto commentPatchDto)  {
        return commentService.updateComment(commentId,commentPatchDto);
    }

    // 아티클 삭제
    @DeleteMapping ("/{comment-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Void> deleteComment (@Positive @PathVariable ("comment-id") Long commentId) {
        return commentService.removeComment(commentId);
    }


}
