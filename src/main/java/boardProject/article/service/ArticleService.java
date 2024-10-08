package boardProject.article.service;

import boardProject.article.dto.ArticleMultiResponseDto;
import boardProject.article.dto.ArticleRequestDto;
import boardProject.article.dto.ArticleResponseDto;
import boardProject.article.entity.Article;
import boardProject.article.mapper.ArticleMapper;
import boardProject.article.repository.ArticleRepository;
import boardProject.article.response.MultiArticleResponse;
import boardProject.article.response.SingleArticleResponse;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;

    public Response<SingleArticleResponse> findArticle(Long articleId) throws BusinessLogicException {

        Article foundArticle = articleRepository.findById(articleId)
                                                .orElseThrow(
                                                        () -> new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST)
                                                );

        ArticleResponseDto articleResponseDto =  mapper.ArticleToArticleResponseDto(foundArticle);

        return new Response<>(StatusCode.SELECT_SUCCESS, SingleArticleResponse.of(articleResponseDto));

    }

    public Response<MultiArticleResponse> findArticles(int page, int size) {

        checkArticleRepoEmpty();

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Article> pageInfo = articleRepository.findAll(pageable);

        List<Article> articles = articleRepository.findAll(pageable).getContent();

        List<ArticleMultiResponseDto> multiResponseDtos = articles.stream()
                                                          .map(article -> mapper.ArticleToMultiResponseDto(article))
                                                          .collect(Collectors.toList());

        return new Response<>(StatusCode.SELECT_SUCCESS,MultiArticleResponse.of(multiResponseDtos,pageInfo));

    }
    public Response<Void> createArticle(ArticleRequestDto articleRequestDto) {

        Article article = Article.builder()
                                            .title(articleRequestDto.getTitle())
                                            .body(articleRequestDto.getBody())
                                            .build();

        articleRepository.save(article);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateArticle(Long articleId, ArticleRequestDto articleRequestDto)
                                                                throws BusinessLogicException {


        Article articleInDb = articleRepository.findById(articleId)
                                               .orElseThrow(
                                                       () -> new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST)
                                               );

        Article articleinfo = mapper.ArticlePostDtoToArticle(articleRequestDto);

        articleInDb.updateArticle(articleinfo.getTitle(),articleinfo.getBody());

        articleRepository.save(articleInDb);

        return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeArticle(Long articleId) throws BusinessLogicException {

        checkArticleExistOrThrow(articleId);

        articleRepository.deleteById(articleId);

        return new Response<>(StatusCode.DELETE_SUCCESS, null);

    }

    public void checkArticleExistOrThrow(Long articleId) throws BusinessLogicException {
        if(articleRepository.existsById(articleId) == false) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }

    public void checkArticleRepoEmpty() throws BusinessLogicException {
        if (articleRepository.count() == 0) {
            throw new BusinessLogicException(StatusCode.ARTICLE_REPO_EMPTY);
        }
    }


}
