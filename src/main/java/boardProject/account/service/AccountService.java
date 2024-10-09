package boardProject.account.service;

import boardProject.account.dto.AccountPatchDto;
import boardProject.account.dto.AccountResponseDto;
import boardProject.account.dto.AccountSignUpDto;
import boardProject.account.entity.Account;
import boardProject.account.mapper.AccountMapper;
import boardProject.account.repository.AccountRepository;
import boardProject.account.response.SingleAccountResponse;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper mapper;

    public Response<SingleAccountResponse> findAccount(Long accountId) throws BusinessLogicException {

        Account foundAccount = accountRepository.findById(accountId)
                                                .orElseThrow(
                                                        () -> new BusinessLogicException(StatusCode.ACCOUNT_NOT_EXIST)
                                                );

        AccountResponseDto accountResponseDto =  mapper.accountToAccountResponseDto(foundAccount);

        return new Response<>(StatusCode.SELECT_SUCCESS, SingleAccountResponse.of(accountResponseDto));

    }

    public Response<Void> createAccount(@Valid AccountSignUpDto accountSignUpDto) {


        Account accountInfo = mapper.accountSignUpDtoToAccount(accountSignUpDto);


        Account account =
                Account.builder().name(accountInfo.getName())
                                 .residentNum(accountInfo.getResidentNum())
                                 .birthDay(getBirthdayFromResidentNum(accountInfo.getResidentNum()))
                                 .age(getAgeFromBirthday(getBirthdayFromResidentNum(accountInfo.getResidentNum())))
                                 .phoneNum(accountInfo.getPhoneNum())
                                 .nickname(accountInfo.getNickname())
                                 .email(accountInfo.getEmail())
                                 .password(accountInfo.getPassword())
                                 .build();


        accountRepository.save(account);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateAccount(Long accountId, AccountPatchDto accountPatchDto)
                                                                throws BusinessLogicException {


        Account accountInDb = accountRepository.findById(accountId)
                                               .orElseThrow(
                                                       () -> new BusinessLogicException(StatusCode.ACCOUNT_NOT_EXIST)
                                               );

        Account accountInfo = mapper.accountPatchDtoToAccount(accountPatchDto);

        accountInDb.updateAccount(accountInfo.getEmail(),accountInfo.getPassword(),accountInfo.getPhoneNum(),accountInfo.getNickname());

        accountRepository.save(accountInDb);

        return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeAccount(Long accountId) throws BusinessLogicException {

        checkAccountExistOrThrow(accountId);
        accountRepository.deleteById(accountId);

        return new Response<>(StatusCode.DELETE_SUCCESS, null);

    }


    public void checkAccountExistOrThrow(Long accountId) throws BusinessLogicException {
        if(accountRepository.existsById(accountId) == false) {
            throw new BusinessLogicException(StatusCode.ARTICLE_NOT_EXIST);
        }
    }


    public String getBirthdayFromResidentNum (String residentNum) {

        String birthday = "";
        String monthAndDay = residentNum.substring(2,5);

        if( Year.now().getValue()  <= Integer.valueOf(residentNum.substring(0,1))) {
            birthday = "19" + monthAndDay;
        }
        else {
            birthday = "20" + monthAndDay;
        }

        return birthday;

    }

    public int getAgeFromBirthday (String birthday) {

        return Year.now().getValue() - Integer.valueOf(birthday.substring(0,3));
    }


}
