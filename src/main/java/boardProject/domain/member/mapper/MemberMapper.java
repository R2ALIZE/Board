package boardProject.domain.member.mapper;

import boardProject.domain.member.dto.MemberResponseDto;
import boardProject.domain.member.dto.MemberSignUpDto;
import boardProject.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member memberSignUpDtoToMember (MemberSignUpDto memberSignUpDto);

    MemberResponseDto memberToMemberResponseDto (Member member);
}
