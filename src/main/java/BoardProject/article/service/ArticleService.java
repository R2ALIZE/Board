package BoardProject.article.service;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;
import BoardProject.article.mapper.ArticleMapper;
import BoardProject.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {


    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;

    public ArticleDto.Response findArticle(Long userId) throws Exception {

        Article foundArticle = Optional.ofNullable(articleRepository.findById(userId))
                                        .orElseThrow(Exception::new).get();

         return mapper.convertArticleToArticleResponseDto(foundArticle);

    }

    public Page<Article> findArticles(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.ASC);

        return articleRepository.findAll(pageable);

    }

    public void createArticle(ArticleDto.Request articlePostDto) {

        Article articleInfo = mapper.convertArticlePostDtoToArticle(articlePostDto);

        Article article = Article.builder()
                                            .title(articleInfo.getTitle())
                                            .body(articleInfo.getBody())
                                            .build();

        articleRepository.save(article);
    }

    public void updateArticle(Long articleId, ArticleDto.Request articleRequestDto) throws Exception {

        Article articleInfo = mapper.convertArticlePostDtoToArticle(articleRequestDto);

        Article ArticleInDb = articleRepository.findById(articleId)
                                               .orElseThrow(Exception::new);

        ArticleInDb.updateArticle(articleInfo.getTitle(),articleInfo.getBody());

     articleRepository.save(ArticleInDb);
    }

    public void removeArticle(Long articleId) {

        articleRepository.deleteById(articleId);
    }




}
