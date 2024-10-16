package boardProject.account.service;

import boardProject.account.dto.AccountPatchDto;
import boardProject.account.entity.Account;
import boardProject.account.repository.AccountRepository;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AccountServiceHelper {

    @Autowired
    private AccountRepository accountRepository;


    /** 생성 메서드 **/


    public Account AccountBuilder(Account accountInfo) {
        return
                Account.builder().name(accountInfo.getName())
                        .residentNum(accountInfo.getResidentNum())
                        .birthday(getBirthdayFromResidentNum(accountInfo.getResidentNum()))
                        .age(getAgeFromBirthday(
                                        getBirthdayFromResidentNum(accountInfo.getResidentNum())
                                )
                        )
                        .phoneNum(accountInfo.getPhoneNum())
                        .nickname(accountInfo.getNickname())
                        .email(accountInfo.getEmail())
                        .password(accountInfo.getPassword())
                        .build();
    }


    /** 검증 메서드 **/

    public void checkAccountExistOrThrow(Long accountId) throws BusinessLogicException {
        if (accountRepository.existsById(accountId) == false) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }


    /** repository에서 값 가져오기 **/


    public Account findSpecificArticleById(Long accountId) throws BusinessLogicException {
        return accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new BusinessLogicException(StatusCode.ACCOUNT_NOT_EXIST)
                );
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


    public Account updateAccountFromDto (AccountPatchDto patchDto, Account existingAccount) {

        Field[] fields = AccountPatchDto.class.getDeclaredFields();

        List<Field> nonConstantFields = Arrays.stream(fields)
                .filter(
                        field -> Modifier.isFinal(field.getModifiers()) == false
                )
                .collect(Collectors.toList());


        Account.AccountBuilder builder = existingAccount.toBuilder();


        try {

            for (Field patchDtoField : nonConstantFields) {

                patchDtoField.setAccessible(true);

                String patchDtoFieldName = patchDtoField.getName();

                String getterMethodName = "get"
                        + patchDtoFieldName.substring(0, 1).toUpperCase()
                        + patchDtoFieldName.substring(1, patchDtoFieldName.length());


                Method getterMethodOfDto = AccountPatchDto.class.getMethod(getterMethodName);
                Method builderMethod = Account.AccountBuilder.class.getMethod(patchDtoFieldName,patchDtoField.getType());

                Object getterResult = getterMethodOfDto.invoke(patchDto);

                if (getterResult == null) {
                    continue;
                }

                if (getterResult != null) {

                    if (getterResult.equals("blank")) {
                        builderMethod.invoke(builder,null);
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





