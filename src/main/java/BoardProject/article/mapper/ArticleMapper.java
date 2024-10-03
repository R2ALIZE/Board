package BoardProject.article.mapper;

import BoardProject.article.dto.ArticleMultiResponseDto;
import BoardProject.article.dto.ArticleRequestDto;
import BoardProject.article.dto.ArticleResponseDto;
import BoardProject.article.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {


    // DTO - ENTITY 간의 변환 책임을 맡은 Mapper 클래스

    Article ArticlePostDtoToArticle (ArticleRequestDto articleRequestDto);

    ArticleResponseDto ArticleToArticleResponseDto (Article article);

    ArticleMultiResponseDto ArticleToMultiResponseDto (Article article);
}
