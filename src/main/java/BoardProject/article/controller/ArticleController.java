package BoardProject.article.controller;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 단일 조회
    @GetMapping ("/{article-id}")
    public ResponseEntity<ArticleDto.Response> getArticle (@PathVariable("article-id") Long articleId) throws Exception {
      ArticleDto.Response response = articleService.findArticle(articleId);
        return ResponseEntity.ok(response);
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    public void getArticles (@RequestParam (value = "page", defaultValue = "0") int page,
                             @RequestParam (value = "size", defaultValue = "10") int size) {
      articleService.findArticles(page,size);
    }

    // 아티클 생성
    @PostMapping
    public void postArticle (@RequestBody ArticleDto.Request articleRequestDto) {
        articleService.createArticle(articleRequestDto);
    }

    // 아티클 수정
    @PatchMapping ("/{article-id}")
    public ResponseEntity<ArticleDto.Response> patchArticle (@PathVariable ("article-id") Long articleId,
                              @RequestBody ArticleDto.Request articleRequestDto) throws Exception {

        articleService.updateArticle(articleId,articleRequestDto);

        return new ResponseEntity<ArticleDto.Response>(HttpStatusCode.valueOf(200));
    }

    // 아티클 삭제
    @DeleteMapping ("/{article-id}")
    public void deleteArticle (@PathVariable ("article-id") Long articleId) {

        articleService.removeArticle(articleId);
    }


}
