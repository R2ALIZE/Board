package boardProject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private Long id;

    private String name;

    private String nickname;

    private int age;

    private String birthday;

    private String phoneNum;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


}
