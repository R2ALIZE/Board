package boardProject.account.entity;

import boardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

@Entity
@Table(name = "account")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Proxy를 이용한 지연로딩
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String  birthDay;

    @Column
    @NotNull
    private int age;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
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



    public void updateAccount(String email, String password, String nickname, String phoneNum) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
    }
}
