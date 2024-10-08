package boardProject.article.controller;

import boardProject.article.dto.ArticleMultiResponseDto;
import boardProject.article.dto.ArticleRequestDto;
import boardProject.article.dto.ArticleResponseDto;
import boardProject.article.response.MultiArticleResponse;
import boardProject.article.response.SingleArticleResponse;
import boardProject.article.service.ArticleService;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping ("/{article-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<SingleArticleResponse> getArticle
    (@Positive @PathVariable(name = "article-id") Long articleId) throws Exception {
        return articleService.findArticle(articleId);
    }

    // 복수 조회 (페이징 처리)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<MultiArticleResponse> getArticles
    (@Positive @RequestParam (value = "page", defaultValue = "0") int page,
     @Positive @RequestParam (value = "size", defaultValue = "10") int size) {
       return articleService.findArticles(page,size);
    }

    // 아티클 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> postArticle (@RequestBody @Valid ArticleRequestDto articleRequestDto) {
      return articleService.createArticle(articleRequestDto);
    }

    // 아티클 수정
    @PatchMapping ("/{article-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> patchArticle (@Positive @PathVariable ("article-id") Long articleId,
                                        @RequestBody ArticleRequestDto articleRequestDto) throws Exception {
      return articleService.updateArticle(articleId,articleRequestDto);
    }

    // 아티클 삭제
    @DeleteMapping ("/{article-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Void> deleteArticle (@Positive @PathVariable ("article-id") Long articleId) {
       return articleService.removeArticle(articleId);
    }


}
