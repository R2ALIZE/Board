package boardProject.domain.comment.mapper;

import boardProject.domain.comment.dto.CommentMultiResponseDto;
import boardProject.domain.comment.dto.CommentResponseDto;
import boardProject.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper (componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentResponseDto CommentToCommentResponseDto (Comment comment);

    CommentMultiResponseDto CommentToMultiResponseDto (Comment Comment);
}
