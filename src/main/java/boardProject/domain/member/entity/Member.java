package boardProject.domain.member.entity;


import boardProject.domain.article.entity.Article;
import boardProject.domain.comment.entity.Comment;
import boardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder (toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Proxy를 이용한 지연로딩
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String birthday;

    @Column
    @NotNull
    private int age;


    @Column
    @NotNull
    private String gender;

    @Column
    @NotNull
    private String email;

    @Column
    private String password;

    @Column
    @NotNull
    private String phoneNum;

    @Column
    @NotNull
    private String residentNum;

    @Column
    @NotNull
    private String nickname;


    @Column
    private String description;


    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();


}
