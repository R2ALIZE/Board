package boardProject.domain.member.service;

import boardProject.domain.member.dto.MemberPatchDto;
import boardProject.domain.member.dto.MemberResponseDto;
import boardProject.domain.member.entity.Member;
import boardProject.domain.member.response.SingleMemberResponse;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class MemberService {

    @Autowired
    private MemberServiceHelper helper;


    public Response<SingleMemberResponse> findMember(Long memberId) {

        Member existingMember = helper.findSpecificMemberById(memberId);
        MemberResponseDto memberResponseDto = helper.convertToResponseDto(existingMember);
        SingleMemberResponse response = helper.convertToSingleResponse(memberResponseDto);

        return new Response<>(StatusCode.SELECT_SUCCESS, response);

    }

    public Response<Void> updateMember(Long memberId, MemberPatchDto patchDto)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

       Member existingMember = helper.findSpecificMemberById(memberId);
       Member updatedMember =  helper.updateMemberFromDto(patchDto,existingMember);
       helper.saveMember(updatedMember);

       return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }


    

}
