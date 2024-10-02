package BoardProject.article.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ArticleDto {

    private String title;

    private String body;


    public static class Request { // Client에서 Article 생성할 때 입력하는 정보

        private String title;

        private String body;


    }

    public static class Response {


        private String author;

        private String title;

        private String body;

        // 메타데이터
        private Date createdAt;
        private Date modifiedAt;



    }

}
