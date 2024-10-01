package BoardProject.article.service;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;
import BoardProject.article.mapper.ArticleMapper;
import BoardProject.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {


    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper mapper;

    public void findArticle(Long userId) {
        articleRepository.findById(userId);
    }

    public List<Article> findArticles() {
       return articleRepository.findAll();
    }

    public void createArticle(ArticleDto.Post articlePostDto) {

        Article article = mapper.convertArticlePostDtoToArticle(articlePostDto);

        articleRepository.save(article);

    }

    public void updateArticle(Long articleId) {
        articleRepository.findById(articleId);
    }

    public void removeArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }




}
