package boardProject.domain.article.repository;

import boardProject.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Optional<Article> findById(Long articleId);
    Page<Article> findAll(Pageable pageable);

}
