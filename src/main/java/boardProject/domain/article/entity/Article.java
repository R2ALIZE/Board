package boardProject.domain.article.entity;

import boardProject.domain.comment.entity.Comment;
import boardProject.domain.member.entity.Member;
import boardProject.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder(toBuilder = true)
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany (mappedBy = "article")
    @Builder.Default
    List<Comment> comments = new ArrayList<>();

}
