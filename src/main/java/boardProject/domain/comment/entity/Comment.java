package boardProject.domain.comment.entity;

import boardProject.domain.account.entity.Account;
import boardProject.domain.article.entity.Article;
import boardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String body;




    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;





}
