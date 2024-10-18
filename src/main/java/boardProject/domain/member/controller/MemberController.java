package boardProject.domain.member.controller;

import boardProject.domain.member.dto.MemberPatchDto;
import boardProject.domain.member.dto.MemberSignUpDto;
import boardProject.domain.member.response.SingleMemberResponse;
import boardProject.domain.member.service.MemberService;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
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

    // 계정 생성
    @PostMapping ("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> signUp (@RequestBody @Valid MemberSignUpDto memberSignUpDto) {
      return memberService.createMember(memberSignUpDto);
    }

    // 아티클 수정
    @PatchMapping ("/info/modification/{member-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> patchMember (@Positive @PathVariable ("member-id") Long memberId,
                                        @RequestBody MemberPatchDto patchDto) throws Exception {
      return memberService.updateMember(memberId,patchDto);
    }

    @DeleteMapping ("/{member-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Void> deleteMember (@Positive @PathVariable ("member-id") Long memberId) {
       return memberService.removeMember(memberId);
    }


}
