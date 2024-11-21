package boardProject.domain.member.controller;

import boardProject.domain.member.dto.MemberPatchDto;
import boardProject.domain.member.response.SingleMemberResponse;
import boardProject.domain.member.service.MemberService;
import boardProject.global.response.Response;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/info")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 단일 조회
    @GetMapping ("/{member-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<SingleMemberResponse> getMember
    (@Positive @PathVariable(name = "member-id") Long memberId) throws Exception {
        return memberService.findMember(memberId);
    }


    // 아티클 수정
    @PatchMapping ("/modification/{member-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> patchMember (@Positive @PathVariable ("member-id") Long memberId,
                                        @RequestBody MemberPatchDto patchDto) throws Exception {
      return memberService.updateMember(memberId,patchDto);
    }



}
