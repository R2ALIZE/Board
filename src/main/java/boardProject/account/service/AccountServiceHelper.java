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
import java.time.Year;
import java.util.Arrays;

@Component
public class AccountServiceHelper {

    @Autowired
    private AccountRepository accountRepository;




    /** 생성 메서드 **/


   public Account AccountBuilder (Account accountInfo) {
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
        if(accountRepository.existsById(accountId) == false) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }



    /** repository에서 값 가져오기 **/


   public Account findSpecificArticleById (Long accountId) throws BusinessLogicException {
       return accountRepository.findById(accountId)
               .orElseThrow(
                       () -> new BusinessLogicException(StatusCode.ACCOUNT_NOT_EXIST)
               );
   }


    /** 가공 메서드 **/
    public String getBirthdayFromResidentNum (String residentNum) {

        String birthday = "";
        String monthAndDay = residentNum.substring(0,6);

        if( Year.now().getValue() - 2000  <= Integer.valueOf(residentNum.substring(0,2))) {
            birthday = "19" + monthAndDay;
        }
        else {
            birthday = "20" + monthAndDay;
        }

        return birthday;

    }
    public int getAgeFromBirthday (String birthday) {

        if (TimeUtil.isTodayMonthDayAfter(birthday)) { //생일 지남
            return Year.now().getValue() - Integer.valueOf(birthday.substring(0,4));
        } else {
            return Year.now().getValue() - Integer.valueOf(birthday.substring(0,4))-1;
        }
    }

    public void updateAccountFromDto (AccountPatchDto patchDto, Account account) {

        try {
            Field[] patchDtoFields = AccountPatchDto.class.getDeclaredFields();
            Field[] AccountFields = Account.class.getDeclaredFields();

            for (Field patchDtoField : patchDtoFields) {

                String patchDtoFieldName = patchDtoField.getName();

                // PatchDto에서 getter 메서드 이름 추출
                String getterOfPatchDto = "get" + patchDtoFieldName.substring(0, 1).toUpperCase()
                        + patchDtoFieldName.substring(1);


                // PatchDto에 있는 필드와 동일한 이름을 가진 필드를 Account에서 가져오기
                String extractedFieldNameFromAccount = Arrays.stream(AccountFields)
                        .filter(
                                field -> field.getName()
                                        .equals(patchDtoFieldName.substring(0, patchDtoFieldName.length()))
                        )
                        .map(Field::getName)
                        .findAny()
                        .orElse("");

                // ex) updatename -> updateName
                String setterOfAccount = "update" + extractedFieldNameFromAccount.substring(0, 1).toUpperCase()
                        + extractedFieldNameFromAccount.substring(1);

                // AccountPatchDto의 getter 메서드
                Method getterMethodOfPatchDto = AccountPatchDto.class.getMethod(getterOfPatchDto);

                // Account의 setter 메서드 (updateXXX)
                Method setterMethodOfAccount = Account.class.getMethod(setterOfAccount, patchDtoField.getType());

                Object getterResult = getterMethodOfPatchDto.invoke(patchDto);


                if (getterResult != null) {
                    setterMethodOfAccount.invoke(account, getterResult);
                }
            }

            accountRepository.save(account);
        } catch (NoSuchMethodException ne) {
            ne.printStackTrace();
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        }
    }
}
