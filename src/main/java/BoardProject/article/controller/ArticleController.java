package BoardProject.article.controller;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 단일 조회
    @GetMapping ("/{article-id}")
    public void getArticle (@PathVariable("article-id") Long articleId) {
        articleService.findArticle(articleId);
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    public void getArticles () {
        articleService.findArticles();
    }

    // 아티클 생성
    @PostMapping
    public void postArticle (@RequestBody ArticleDto.Post articlePostDto) {
        articleService.createArticle(articlePostDto);
    }

    // 아티클 수정
    @PatchMapping ("/{article-id}")
    public void patchArticle (@PathVariable ("article-id") Long articleId ) {
        articleService.updateArticle(articleId);
    }

    // 아티클 삭제
    @DeleteMapping ("/{article-id}")
    public void deleteArticle (@PathVariable ("article-id") Long articleId) {
        articleService.removeArticle(articleId);
    }


}
