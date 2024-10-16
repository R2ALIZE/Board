package boardProject.account.controller;

import boardProject.account.dto.AccountPatchDto;
import boardProject.account.dto.AccountSignUpDto;
import boardProject.account.response.SingleAccountResponse;
import boardProject.account.service.AccountService;
import boardProject.global.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // 단일 조회
    @GetMapping ("/{account-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<SingleAccountResponse> getAccount
    (@Positive @PathVariable(name = "account-id") Long accountId) throws Exception {
        return accountService.findAccount(accountId);
    }

    // 계정 생성
    @PostMapping ("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> signUp (@RequestBody @Valid AccountSignUpDto accountSignUpDto) {
      return accountService.createAccount(accountSignUpDto);
    }

    // 아티클 수정
    @PatchMapping ("/info/modification/{account-id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> patchAccount (@Positive @PathVariable ("account-id") Long accountId,
                                        @RequestBody AccountPatchDto patchDto) throws Exception {
      return accountService.updateAccount(accountId,patchDto);
    }

    @DeleteMapping ("/{account-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Void> deleteAccount (@Positive @PathVariable ("account-id") Long accountId) {
       return accountService.removeAccount(accountId);
    }


}
