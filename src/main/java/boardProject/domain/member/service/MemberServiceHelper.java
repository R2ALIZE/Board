package boardProject.domain.member.service;

import boardProject.domain.member.dto.MemberPatchDto;
import boardProject.domain.member.dto.MemberResponseDto;
import boardProject.domain.member.dto.MemberSignUpDto;
import boardProject.domain.member.entity.Member;
import boardProject.domain.member.mapper.MemberMapper;
import boardProject.domain.member.repository.MemberRepository;
import boardProject.domain.member.response.SingleMemberResponse;
import boardProject.global.constant.Constants;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.global.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Year;

@Component
public class MemberServiceHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberMapper mapper;


    /** DB 접근 메서드 **/


    public Member findSpecificArticleById(Long memberId) throws BusinessLogicException {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new BusinessLogicException(StatusCode.ACCOUNT_NOT_EXIST)
                );
    }

    public void saveMember (Member member) {
        memberRepository.save(member);
    }

    public void deleteMember (Member member) {
        memberRepository.delete(member);
    }




    /** 변환 메서드 **/


    public MemberResponseDto convertToResponseDto (Member member) {
        return mapper.memberToMemberResponseDto(member);
    }

    public Member convertToMember (MemberSignUpDto signUpDto) {
        return mapper.memberSignUpDtoToMember(signUpDto);
    }


    public SingleMemberResponse convertToSingleResponse (MemberResponseDto responseDto) {
        return SingleMemberResponse.of(responseDto);
    }



    /** 생성 메서드 **/

    public Member memberBuilder(Member memberInfo) {
        return
                Member.builder().name(memberInfo.getName())
                        .residentNum(addDashResidentNum(memberInfo.getResidentNum()))
                        .birthday(getBirthdayFromResidentNum(memberInfo.getResidentNum()))
                        .age(getAgeFromBirthday(
                                        getBirthdayFromResidentNum(memberInfo.getResidentNum())
                                )
                        )
                        .gender(getGenderFromResidentNum(memberInfo.getResidentNum()))
                        .phoneNum(memberInfo.getPhoneNum())
                        .nickname(memberInfo.getNickname())
                        .email(memberInfo.getEmail())
                        .password(passwordEncoder.encode(memberInfo.getPassword()))
                        .build();
    }


    /** 검증 메서드 **/

    public void checkMemberExistOrThrow(Long memberId) throws BusinessLogicException {
        if (memberRepository.existsById(memberId) == false) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }




    /** 가공 메서드 **/
    public String getBirthdayFromResidentNum(String residentNum) {

        String birthday = "";
        String monthAndDay = residentNum.substring(0, 6);

        if (Year.now().getValue() - 2000 <= Integer.valueOf(residentNum.substring(0, 2))) {
            birthday = "19" + monthAndDay;
        } else {
            birthday = "20" + monthAndDay;
        }

        return birthday;

    }

    public int getAgeFromBirthday(String birthday) {

        if (TimeUtil.isTodayMonthDayAfter(birthday)) { //생일 지남
            return Year.now().getValue() - Integer.valueOf(birthday.substring(0, 4));
        } else {
            return Year.now().getValue() - Integer.valueOf(birthday.substring(0, 4)) - 1;
        }
    }


    public String getGenderFromResidentNum (String residentNum) {

        // resident num form : 000000-0000000

        if (String.valueOf(residentNum.indexOf(7)).equals("1")
            || String.valueOf(residentNum.indexOf(7)).equals("3")) {
            return "MALE";
        }

        if (String.valueOf(residentNum.indexOf(7)).equals("2")
                || String.valueOf(residentNum.indexOf(7)).equals("4")) {
            return "FEMALE";
        }

        return "ERR";
    }


    public String addDashResidentNum (String residentNum) {
        return residentNum.substring(0,6) + "-" + residentNum.substring(6,residentNum.length());
    }






    public Member updateMemberFromDto (MemberPatchDto patchDto, Member existingMember) {

        Field[] fields = MemberPatchDto.class.getDeclaredFields();

        Member.MemberBuilder builder = existingMember.toBuilder();


        try {

            for (Field patchDtoField : fields) {

                patchDtoField.setAccessible(true);

                String patchDtoFieldName = patchDtoField.getName();

                String getterMethodName = "get"
                        + patchDtoFieldName.substring(0, 1).toUpperCase()
                        + patchDtoFieldName.substring(1, patchDtoFieldName.length());


                Method getterMethodOfDto = MemberPatchDto.class.getMethod(getterMethodName);
                Method builderMethod = Member.MemberBuilder.class.getMethod(patchDtoFieldName,patchDtoField.getType());

                Object getterResult = getterMethodOfDto.invoke(patchDto);

                if (getterResult == null) {
                    continue;
                }

                if (getterResult != null) {

                    if (getterResult.equals(Constants.EXPRESSION_OF_EXPLICIT_NULL)) {
                        builderMethod.invoke(builder,"");
                    } else {
                        builderMethod.invoke(builder,getterResult);
                    }

                }

            }
        } catch (NoSuchMethodException nme) {
            nme.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return builder.build();
    }
}





