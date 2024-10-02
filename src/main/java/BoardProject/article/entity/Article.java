package BoardProject.article.entity;

import BoardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "article")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Proxy를 이용한 지연로딩
@AllArgsConstructor

public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String body;

    public Article articleBuilder (String title,String body, LocalDateTime createdAt) {
        return new Article().builder()
                                        .title(title)
                                        .body(body)
                                        .build();
    }


    public void updateArticle (String title, String body) {
        this.title = title;
        this.body = body;
    }


}
