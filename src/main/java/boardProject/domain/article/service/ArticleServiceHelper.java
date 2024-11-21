package boardProject.domain.article.service;

import boardProject.domain.article.dto.ArticleMultiResponseDto;
import boardProject.domain.article.dto.ArticlePatchDto;
import boardProject.domain.article.dto.ArticlePostDto;
import boardProject.domain.article.dto.ArticleResponseDto;
import boardProject.domain.article.entity.Article;
import boardProject.domain.article.mapper.ArticleMapper;
import boardProject.domain.article.repository.ArticleRepository;
import boardProject.domain.article.response.MultiArticleResponse;
import boardProject.domain.article.response.SingleArticleResponse;
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
public class ArticleServiceHelper {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;


    /** DB 접근 메서드 **/

    public Article findSpecificArticleById (Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(
                        () -> new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST)
                );
    }

    public Page<Article> getArticlePageInfo (int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return articleRepository.findAll(pageable);
    }


    public void saveArticle (Article article) {
        articleRepository.save(article);
    }


    public void deleteArticle (Article article) {
        articleRepository.delete(article);
    }


    public Article updateArticleFromDto (ArticlePatchDto patchDto, Article existingArticle)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] fields = ArticlePatchDto.class.getDeclaredFields();

        Article.ArticleBuilder builder = existingArticle.toBuilder();



        for (Field patchDtoField : fields) {

            patchDtoField.setAccessible(true);

            String patchDtoFieldName = patchDtoField.getName();

            String getterMethodName = "get"
                    + patchDtoFieldName.substring(0, 1).toUpperCase()
                    + patchDtoFieldName.substring(1);


            Method getterMethodOfDto = ArticlePatchDto.class.getMethod(getterMethodName);
            Method builderMethod = Article.ArticleBuilder.class
                                          .getMethod(patchDtoFieldName,patchDtoField.getType());

            Object getterResult = getterMethodOfDto.invoke(patchDto);

            if (getterResult == null) {
                continue;
            }


            if (getterResult.equals(Constants.EXPRESSION_OF_EXPLICIT_NULL)) {
                builderMethod.invoke(builder, (Object) null);
            } else {
                builderMethod.invoke(builder,getterResult);
            }


        }

        return builder.build();
    }






    /** 생성 메서드 **/


    public Article articleBuilder (ArticlePostDto postDto) {

        return Article.builder()
                                .title(postDto.getTitle())
                                .body(postDto.getBody())
                      .build();

    }






    /** 검증 메서드 **/


    public void checkArticleExistOrThrow(Long articleId) throws BusinessLogicException {
        if(!articleRepository.existsById(articleId)) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }

    public void checkArticleRepoEmpty() throws BusinessLogicException {
        if (articleRepository.count() == 0) {
            throw new BusinessLogicException(StatusCode.ARTICLE_REPO_EMPTY);
        }
    }



    /** 변환 메서드 **/


    public ArticleResponseDto convertToResponseDto (Article article) {
        return mapper.ArticleToArticleResponseDto(article);
    }

    public SingleArticleResponse convertToSingleArticleResponse (ArticleResponseDto responseDto) {
        return SingleArticleResponse.of(responseDto);
    }


    public List<ArticleMultiResponseDto> eachArticleToMultiResponseDto (List<Article> articles) {

        return articles.stream()
                       .map(
                               article -> mapper.ArticleToMultiResponseDto(article)
                       )
                       .collect(Collectors.toList());
    }

    public MultiArticleResponse ResponseDtoToMultiResponse (List<ArticleMultiResponseDto> responseDtos,
                                                            Page<Article> pageInfo) {

        return MultiArticleResponse.of(responseDtos, pageInfo);
    }


}
