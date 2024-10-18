package boardProject.domain.member.response;

import boardProject.domain.member.dto.MemberResponseDto;
import lombok.Getter;

@Getter
public class SingleMemberResponse {

    MemberResponseDto memberInfo;


    public SingleMemberResponse(MemberResponseDto memberResponseDto) {
        this.memberInfo = memberResponseDto;
    }

    public static SingleMemberResponse of (MemberResponseDto memberResponseDto) {
        return new SingleMemberResponse(memberResponseDto);
    }

}
