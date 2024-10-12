package boardProject.account.entity;


import boardProject.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "account")
@Getter // dto - entity 간의 매핑 과정에서 mapper가 getter를 통해 접근
@Builder (toBuilder = true)
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
    private String birthday;

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

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updatePhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
