package boardProject.article.mapper;

import boardProject.article.dto.ArticleMultiResponseDto;
import boardProject.article.dto.ArticlePostDto;
import boardProject.article.dto.ArticleResponseDto;
import boardProject.article.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {


    // DTO - ENTITY 간의 변환 책임을 맡은 Mapper 클래스

    Article ArticlePostDtoToArticle (ArticlePostDto articlePostDto);

    ArticleResponseDto ArticleToArticleResponseDto (Article article);

    ArticleMultiResponseDto ArticleToMultiResponseDto (Article article);
}
