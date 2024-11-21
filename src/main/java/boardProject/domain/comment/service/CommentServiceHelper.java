package boardProject.domain.comment.service;

import boardProject.domain.comment.dto.CommentMultiResponseDto;
import boardProject.domain.comment.dto.CommentPatchDto;
import boardProject.domain.comment.dto.CommentPostDto;
import boardProject.domain.comment.dto.CommentResponseDto;
import boardProject.domain.comment.entity.Comment;
import boardProject.domain.comment.mapper.CommentMapper;
import boardProject.domain.comment.repository.CommentRepository;
import boardProject.domain.comment.response.MultiCommentResponse;
import boardProject.domain.comment.response.SingleCommentResponse;
import boardProject.global.constant.Constants;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentServiceHelper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper mapper;


    /** DB 접근 메서드 **/

    public Comment findSpecificCommentById (Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new BusinessLogicException(StatusCode.COMMENT_NOT_EXIST)
                );
    }

    public Page<Comment> getCommentPageInfo (int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return commentRepository.findAll(pageable);
    }


    public void saveComment (Comment comment) {
        commentRepository.save(comment);
    }


    public void deleteComment (Comment comment) {
        commentRepository.delete(comment);
    }


    public Comment updateCommentFromDto (CommentPatchDto patchDto, Comment existingComment)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] fields = CommentPatchDto.class.getDeclaredFields();

        Comment.CommentBuilder builder = existingComment.toBuilder();


        for (Field patchDtoField : fields) {

            patchDtoField.setAccessible(true);

            String patchDtoFieldName = patchDtoField.getName();

            String getterMethodName = "get"
                    + patchDtoFieldName.substring(0, 1).toUpperCase()
                    + patchDtoFieldName.substring(1);


            Method getterMethodOfDto = CommentPatchDto.class.getMethod(getterMethodName);
            Method builderMethod = Comment.CommentBuilder.class
                                          .getMethod(patchDtoFieldName,patchDtoField.getType());

            Object getterResult = getterMethodOfDto.invoke(patchDto);

            if (getterResult == null) {
                continue;
            }

            if (getterResult.equals(Constants.EXPRESSION_OF_EXPLICIT_NULL)) {
                builderMethod.invoke(builder, (Object) null);
            } else {
                builderMethod.invoke(builder, getterResult);
            }

        }

        return builder.build();
    }






    /** 생성 메서드 **/


    public Comment commentBuilder (CommentPostDto postDto) {

        return Comment.builder()
                                .title(postDto.getTitle())
                                .body(postDto.getBody())
                      .build();

    }






    /** 검증 메서드 **/


    public void checkCommentExistOrThrow(Long commentId) throws BusinessLogicException {
        if(!commentRepository.existsById(commentId)) {
            throw new BusinessLogicException(StatusCode.COMMENT_NOT_EXIST);
        }
    }

    public void checkCommentRepoEmpty() throws BusinessLogicException {
        if (commentRepository.count() == 0) {
            throw new BusinessLogicException(StatusCode.COMMENT_REPO_EMPTY);
        }
    }



    /** 변환 메서드 **/


    public CommentResponseDto convertToResponseDto (Comment comment) {
        return mapper.CommentToCommentResponseDto(comment);
    }

    public SingleCommentResponse convertToSingleCommentResponse (CommentResponseDto responseDto) {
        return SingleCommentResponse.of(responseDto);
    }


    public List<CommentMultiResponseDto> eachCommentToMultiResponseDto (List<Comment> comments) {

        return comments.stream()
                       .map(
                               article -> mapper.CommentToMultiResponseDto(article)
                       )
                       .collect(Collectors.toList());
    }

    public MultiCommentResponse ResponseDtoToMultiResponse (List<CommentMultiResponseDto> responseDtos,
                                                            Page<Comment> pageInfo) {

        return MultiCommentResponse.of(responseDtos, pageInfo);
    }


}
