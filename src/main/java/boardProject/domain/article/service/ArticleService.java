package boardProject.domain.article.service;

import boardProject.domain.article.dto.ArticleMultiResponseDto;
import boardProject.domain.article.dto.ArticlePatchDto;
import boardProject.domain.article.dto.ArticlePostDto;
import boardProject.domain.article.dto.ArticleResponseDto;
import boardProject.domain.article.entity.Article;
import boardProject.domain.article.response.MultiArticleResponse;
import boardProject.domain.article.response.SingleArticleResponse;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleServiceHelper helper;

    public Response<SingleArticleResponse> findArticle(Long articleId) {

        Article existingArticle = helper.findSpecificArticleById(articleId);

        ArticleResponseDto articleResponseDto = helper.convertToResponseDto(existingArticle);

        SingleArticleResponse articleResponse = helper.convertToSingleArticleResponse(articleResponseDto);

        return new Response<>(StatusCode.SELECT_SUCCESS, articleResponse);

    }

    public Response<MultiArticleResponse> findArticles(int page, int size) {

        helper.checkArticleRepoEmpty();

        Page<Article> pageInfo = helper.getArticlePageInfo(page,size);

        List<ArticleMultiResponseDto> multiResponseDtos
                = helper.eachArticleToMultiResponseDto(pageInfo.getContent());

        MultiArticleResponse response = helper.ResponseDtoToMultiResponse(multiResponseDtos,pageInfo);

        return new Response<>(StatusCode.SELECT_SUCCESS,response);

    }
    public Response<Void> createArticle(ArticlePostDto articlePostDto) {

        Article createdArticle = helper.articleBuilder(articlePostDto);

        helper.saveArticle(createdArticle);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateArticle(Long articleId, ArticlePatchDto articlePatchDto) {

        Article existingArticle = helper.findSpecificArticleById(articleId);

        Article updatedArticle = helper.updateArticleFromDto(articlePatchDto,existingArticle);

        helper.saveArticle(updatedArticle);

        return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeArticle(Long articleId) {

        Article existingArticle =  helper.findSpecificArticleById(articleId);

        helper.deleteArticle(existingArticle);

        return new Response<>(StatusCode.DELETE_SUCCESS, null);

    }



}
