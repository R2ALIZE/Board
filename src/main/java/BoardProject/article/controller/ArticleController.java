package BoardProject.article.controller;

import BoardProject.article.dto.ArticleDto;
import BoardProject.article.entity.Article;
import BoardProject.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 단일 조회
    @GetMapping (path = "/{article-id}")
    public ResponseEntity<Article> getArticle (@PathVariable(name = "article-id") Long articleId) throws Exception {
      Article response = articleService.findArticle(articleId);
        return ResponseEntity.ok(response);
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    public ResponseEntity<List<Article>> getArticles (@RequestParam (value = "page", defaultValue = "0") int page,
                                                      @RequestParam (value = "size", defaultValue = "10") int size) {

       List<Article> response =  articleService.findArticles(page,size).getContent();

       return new ResponseEntity<>(response,HttpStatusCode.valueOf(200));
    }

    // 아티클 생성
    @PostMapping
    public ResponseEntity postArticle (@RequestBody ArticleDto.Request articleRequestDto) {

        articleService.createArticle(articleRequestDto);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    // 아티클 수정
    @PatchMapping ("/{article-id}")
    public ResponseEntity<Void> patchArticle (@PathVariable ("article-id") Long articleId,
                              @RequestBody ArticleDto.Request articleRequestDto) throws Exception {

        articleService.updateArticle(articleId,articleRequestDto);

        return new ResponseEntity<Void>(HttpStatusCode.valueOf(200));
    }

    // 아티클 삭제
    @DeleteMapping ("/{article-id}")
    public void deleteArticle (@PathVariable ("article-id") Long articleId) {

        articleService.removeArticle(articleId);
    }


}
