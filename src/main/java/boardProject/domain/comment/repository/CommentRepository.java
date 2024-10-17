package boardProject.domain.comment.repository;

import boardProject.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findById(Long articleId);
    Page<Comment> findAll(Pageable pageable);

}
