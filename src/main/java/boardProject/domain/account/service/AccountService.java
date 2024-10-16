package boardProject.domain.account.service;

import boardProject.domain.account.dto.AccountPatchDto;
import boardProject.domain.account.dto.AccountResponseDto;
import boardProject.domain.account.dto.AccountSignUpDto;
import boardProject.domain.account.entity.Account;
import boardProject.domain.account.mapper.AccountMapper;
import boardProject.domain.account.repository.AccountRepository;
import boardProject.domain.account.response.SingleAccountResponse;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import boardProject.global.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountServiceHelper helper;

    @Autowired
    private AccountMapper mapper;

    public Response<SingleAccountResponse> findAccount(Long accountId) throws BusinessLogicException {

        Account foundAccount = helper.findSpecificArticleById(accountId);

        AccountResponseDto accountResponseDto =  mapper.accountToAccountResponseDto(foundAccount);

        return new Response<>(StatusCode.SELECT_SUCCESS, SingleAccountResponse.of(accountResponseDto));

    }

    public Response<Void> createAccount(AccountSignUpDto accountSignUpDto) {

        Account accountInfo = mapper.accountSignUpDtoToAccount(accountSignUpDto);

        Account account = helper.AccountBuilder(accountInfo);

        accountRepository.save(account);

        return new Response<>(StatusCode.INSERT_SUCCESS,null);

    }

    public Response<Void> updateAccount(Long accountId, AccountPatchDto patchDto) throws BusinessLogicException {

       Account existingAccount = helper.findSpecificArticleById(accountId);

       Account updatedAccount =  helper.updateAccountFromDto(patchDto,existingAccount);

       accountRepository.save(updatedAccount);

       return new Response<>(StatusCode.UPDATE_SUCCESS, null);

    }

    public Response<Void> removeAccount(Long accountId) throws BusinessLogicException {

        // 사용자 확인 로직 추가

        helper.checkAccountExistOrThrow(accountId);

        accountRepository.deleteById(accountId);

        return new Response<>(StatusCode.DELETE_SUCCESS, null);
    }

    

}
