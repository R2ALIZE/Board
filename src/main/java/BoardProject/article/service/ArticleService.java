package BoardProject.article.service;

import BoardProject.article.dto.ArticleMultiResponseDto;
import BoardProject.article.dto.ArticleRequestDto;
import BoardProject.article.dto.ArticleResponseDto;
import BoardProject.article.entity.Article;
import BoardProject.article.mapper.ArticleMapper;
import BoardProject.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;

    public ArticleResponseDto findArticle(Long articleId) throws Exception {

        Article foundArticle = Optional.ofNullable(articleRepository.findById(articleId))
                                        .orElseThrow(Exception::new).get();

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
                                               .orElseThrow(Exception::new);

        ArticleInDb.updateArticle(articleRequestDto.getTitle(),articleRequestDto.getBody());

        articleRepository.save(ArticleInDb);

    }

    public void removeArticle(Long articleId) {

        articleRepository.deleteById(articleId);
    }




}
