package BoardProject.article.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Proxy를 이용한 지연로딩
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    private Date createdAt;

}
