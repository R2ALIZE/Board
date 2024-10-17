package boardProject.domain.comment.service;

import boardProject.domain.comment.dto.CommentMultiResponseDto;
import boardProject.domain.comment.dto.CommentPatchDto;
import boardProject.domain.comment.dto.CommentPostDto;
import boardProject.domain.comment.dto.CommentResponseDto;
import boardProject.domain.comment.entity.Comment;
import boardProject.domain.comment.response.MultiCommentResponse;
import boardProject.domain.comment.response.SingleCommentResponse;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentServiceHelper helper;

    public Response<SingleCommentResponse> findComment (Long commentId) {

        Comment existingComment = helper.findSpecificCommentById(commentId);

        CommentResponseDto commentResponseDto = helper.convertToResponseDto(existingComment);

        SingleCommentResponse response = helper.convertToSingleCommentResponse(commentResponseDto);

        return new Response<>(StatusCode.SELECT_SUCCESS, response);

    }

    public Response<MultiCommentResponse> findComments (int page, int size) {

        helper.checkCommentRepoEmpty();

        Page<Comment> pageInfo = helper.getCommentPageInfo(page,size);

        List<CommentMultiResponseDto> multiResponseDtos
                = helper.eachCommentToMultiResponseDto(pageInfo.getContent());

        MultiCommentResponse response = helper.ResponseDtoToMultiResponse(multiResponseDtos,pageInfo);

        return new Response<>(StatusCode.SELECT_SUCCESS,response);

    }
    public Response<Void> createComment (CommentPostDto commentPostDto) {

        Comment createdComment = helper.commentBuilder(commentPostDto);

        helper.saveComment(createdComment);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateComment (Long commentId, CommentPatchDto commentPatchDto) {

        Comment existingComment = helper.findSpecificCommentById(commentId);

        Comment updatedComment = helper.updateCommentFromDto(commentPatchDto,existingComment);

        helper.saveComment(updatedComment);

        return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeComment (Long commentId) {

        Comment existingComment = helper.findSpecificCommentById(commentId);

        helper.deleteComment(existingComment);

        return new Response<>(StatusCode.DELETE_SUCCESS, null);

    }



}
