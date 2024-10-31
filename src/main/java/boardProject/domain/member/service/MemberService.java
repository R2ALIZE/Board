package boardProject.domain.member.service;

import boardProject.domain.member.dto.MemberPatchDto;
import boardProject.domain.member.dto.MemberResponseDto;
import boardProject.domain.member.dto.MemberSignUpDto;
import boardProject.domain.member.entity.Member;
import boardProject.domain.member.response.SingleMemberResponse;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;

@Service
public class MemberService {

    @Autowired
    private MemberServiceHelper helper;


    public Response<SingleMemberResponse> findMember(Long memberId) {

        Member existingMember = helper.findSpecificArticleById(memberId);

        MemberResponseDto memberResponseDto = helper.convertToResponseDto(existingMember);

        SingleMemberResponse response = helper.convertToSingleResponse(memberResponseDto);

        return new Response<>(StatusCode.SELECT_SUCCESS, response);

    }

    public Response<Void> createMember(MemberSignUpDto memberSignUpDto)
            throws GeneralSecurityException, UnsupportedEncodingException {

        Member memberInfo = helper.convertToMember(memberSignUpDto);

        Member createdMember = helper.memberBuilder(memberInfo);

        helper.saveMember(createdMember);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateMember(Long memberId, MemberPatchDto patchDto)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

       Member existingMember = helper.findSpecificArticleById(memberId);

       Member updatedMember =  helper.updateMemberFromDto(patchDto,existingMember);

       helper.saveMember(updatedMember);

       return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeMember(Long memberId) {

        // 사용자 확인 로직 추가
       Member existingMember =  helper.findSpecificArticleById(memberId);

       helper.deleteMember(existingMember);

       return new Response<>(StatusCode.DELETE_SUCCESS, null);
    }

    

}
