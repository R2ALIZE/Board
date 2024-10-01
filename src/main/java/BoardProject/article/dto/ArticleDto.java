package BoardProject.article.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArticleDto {

    private String title;

    private String body;


    public class Post { // Client에서 Article 생성할 때 입력하는 정보

        private String title;

        private String body;

    }

}
