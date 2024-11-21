package boardProject.domain.member.mapper;

import boardProject.domain.member.dto.MemberResponseDto;
import boardProject.domain.member.entity.Member;
import boardProject.global.auth.dto.SignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member memberSignUpDtoToMember (SignUpDto memberSignUpDto);

    MemberResponseDto memberToMemberResponseDto (Member member);
}
