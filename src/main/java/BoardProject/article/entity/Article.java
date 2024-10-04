package BoardProject.article.entity;

import BoardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Proxy를 이용한 지연로딩
@AllArgsConstructor

public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String body;

    public void updateArticle (String title, String body) {
        this.title = title;
        this.body = body;
    }


}
