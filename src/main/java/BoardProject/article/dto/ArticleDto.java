package BoardProject.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;


@Getter
@AllArgsConstructor
public class ArticleDto {

    private String title;

    private String body;


    @Getter
    public static class Request { // Client에서 Article 생성할 때 입력하는 정보

        private String title;

        private String body;


    }


    @Getter
    public static class Response {


        private String title;

        private String body;

        // 메타데이터
        private Date createdAt;
        private Date modifiedAt;



    }

}
