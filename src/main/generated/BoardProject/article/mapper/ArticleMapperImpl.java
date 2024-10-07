package BoardProject.article.mapper;

import BoardProject.article.dto.ArticleMultiResponseDto;
import BoardProject.article.dto.ArticleRequestDto;
import BoardProject.article.dto.ArticleResponseDto;
import BoardProject.article.entity.Article;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-07T14:06:11+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Azul Systems, Inc.)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article ArticlePostDtoToArticle(ArticleRequestDto articleRequestDto) {
        if ( articleRequestDto == null ) {
            return null;
        }

        Article.ArticleBuilder article = Article.builder();

        article.title( articleRequestDto.getTitle() );
        article.body( articleRequestDto.getBody() );

        return article.build();
    }

    @Override
    public ArticleResponseDto ArticleToArticleResponseDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleResponseDto.ArticleResponseDtoBuilder articleResponseDto = ArticleResponseDto.builder();

        articleResponseDto.title( article.getTitle() );
        articleResponseDto.body( article.getBody() );
        articleResponseDto.createdAt( article.getCreatedAt() );
        articleResponseDto.modifiedAt( article.getModifiedAt() );

        return articleResponseDto.build();
    }

    @Override
    public ArticleMultiResponseDto ArticleToMultiResponseDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleMultiResponseDto.ArticleMultiResponseDtoBuilder articleMultiResponseDto = ArticleMultiResponseDto.builder();

        articleMultiResponseDto.id( article.getId() );
        articleMultiResponseDto.title( article.getTitle() );
        articleMultiResponseDto.body( article.getBody() );
        articleMultiResponseDto.createdAt( article.getCreatedAt() );
        articleMultiResponseDto.modifiedAt( article.getModifiedAt() );

        return articleMultiResponseDto.build();
    }
}
