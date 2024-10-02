package BoardProject.article.service;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;
import BoardProject.article.mapper.ArticleMapper;
import BoardProject.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {


    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;

    public Article findArticle(Long articleId) throws Exception {

        Article foundArticle = Optional.ofNullable(articleRepository.findById(articleId))
                                        .orElseThrow(Exception::new).get();

         return foundArticle;

    }

    public Page<Article> findArticles(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        return articleRepository.findAll(pageable);

    }

    public void createArticle(ArticleDto.Request articleRequestDto) {

        Article article = Article.builder()
                                            .title(articleRequestDto.getTitle())
                                            .body(articleRequestDto.getBody())
                                            .build();

        articleRepository.save(article);
    }

    public void updateArticle(Long articleId, ArticleDto.Request articleRequestDto) throws Exception {


        Article ArticleInDb = articleRepository.findById(articleId)
                                               .orElseThrow(Exception::new);

        ArticleInDb.updateArticle(articleRequestDto.getTitle(),articleRequestDto.getBody());

        articleRepository.save(ArticleInDb);

    }

    public void removeArticle(Long articleId) {

        articleRepository.deleteById(articleId);
    }




}
