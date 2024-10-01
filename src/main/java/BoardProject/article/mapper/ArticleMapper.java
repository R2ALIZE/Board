package BoardProject.article.mapper;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;

public interface ArticleMapper {


    // DTO - ENTITY 간의 변환 책임을 맡은 Mapper 클래스

    Article convertArticlePostDtoToArticle (ArticleDto.Post articlePostDto);
}
