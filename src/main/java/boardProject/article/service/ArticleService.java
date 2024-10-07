package boardProject.article.service;

import boardProject.article.dto.ArticleMultiResponseDto;
import boardProject.article.dto.ArticleRequestDto;
import boardProject.article.dto.ArticleResponseDto;
import boardProject.article.entity.Article;
import boardProject.article.mapper.ArticleMapper;
import boardProject.article.repository.ArticleRepository;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ArticleResponseDto findArticle(Long articleId) throws BusinessLogicException {

        Article foundArticle = articleRepository.findById(articleId)
                                                .orElseThrow(
                                                        () -> new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST)
                                                );

        ArticleResponseDto response =  mapper.ArticleToArticleResponseDto(foundArticle);

        return response;
    }

    public List<ArticleMultiResponseDto> findArticles(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        List<Article> articles = articleRepository.findAll(pageable).getContent();

        List<ArticleMultiResponseDto> response = articles.stream()
                                                          .map(article -> mapper.ArticleToMultiResponseDto(article))
                                                          .collect(Collectors.toList());
        return response;

    }
    public void createArticle(ArticleRequestDto articleRequestDto) {

        Article article = Article.builder()
                                            .title(articleRequestDto.getTitle())
                                            .body(articleRequestDto.getBody())
                                            .build();

        articleRepository.save(article);
    }

    public void updateArticle(Long articleId, ArticleRequestDto articleRequestDto) throws Exception {


        Article ArticleInDb = articleRepository.findById(articleId)
                                               .orElseThrow(
                                                       () -> new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST)
                                               );

        ArticleInDb.updateArticle(articleRequestDto.getTitle(),articleRequestDto.getBody());

        articleRepository.save(ArticleInDb);

    }

    public void removeArticle(Long articleId) {

        articleRepository.deleteById(articleId);
    }




}
