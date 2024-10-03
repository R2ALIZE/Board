package BoardProject.article.controller;

import BoardProject.article.dto.ArticleMultiResponseDto;
import BoardProject.article.dto.ArticleRequestDto;
import BoardProject.article.dto.ArticleResponseDto;
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
    public ResponseEntity<ArticleResponseDto> getArticle
    (@PathVariable(name = "article-id") Long articleId) throws Exception {
        return ResponseEntity.ok(articleService.findArticle(articleId));
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    public ResponseEntity<List<ArticleMultiResponseDto>> getArticles
    (@RequestParam (value = "page", defaultValue = "0") int page,
     @RequestParam (value = "size", defaultValue = "10") int size) {
       return new ResponseEntity<>(articleService.findArticles(page,size),HttpStatusCode.valueOf(200));
    }

    // 아티클 생성
    @PostMapping
    public ResponseEntity postArticle (@RequestBody ArticleRequestDto articleRequestDto) {
        articleService.createArticle(articleRequestDto);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    // 아티클 수정
    @PatchMapping ("/{article-id}")
    public ResponseEntity patchArticle (@PathVariable ("article-id") Long articleId,
                                        @RequestBody ArticleRequestDto articleRequestDto) throws Exception {
        articleService.updateArticle(articleId,articleRequestDto);
        return new ResponseEntity (HttpStatusCode.valueOf(200));
    }

    // 아티클 삭제
    @DeleteMapping ("/{article-id}")
    public void deleteArticle (@PathVariable ("article-id") Long articleId) {
        articleService.removeArticle(articleId);
    }


}
