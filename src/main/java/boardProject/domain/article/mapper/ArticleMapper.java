package boardProject.domain.article.mapper;

import boardProject.domain.article.dto.ArticleMultiResponseDto;
import boardProject.domain.article.dto.ArticlePostDto;
import boardProject.domain.article.dto.ArticleResponseDto;
import boardProject.domain.article.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper (componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {

    ArticleResponseDto ArticleToArticleResponseDto (Article article);

    ArticleMultiResponseDto ArticleToMultiResponseDto (Article article);
}
