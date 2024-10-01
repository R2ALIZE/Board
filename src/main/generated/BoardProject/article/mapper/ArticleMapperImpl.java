package BoardProject.article.mapper;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-01T15:19:38+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Azul Systems, Inc.)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article convertArticlePostDtoToArticle(ArticleDto.Post articlePostDto) {
        if ( articlePostDto == null ) {
            return null;
        }

        Article.ArticleBuilder article = Article.builder();

        return article.build();
    }
}
