package boardProject.account.response;

import boardProject.account.dto.AccountResponseDto;
import lombok.Getter;

@Getter
public class SingleAccountResponse {

    AccountResponseDto accountInfo;


    public SingleAccountResponse(AccountResponseDto accountResponseDto) {
        this.accountInfo = accountResponseDto;
    }

    public static SingleAccountResponse of (AccountResponseDto accountResponseDto) {
        return new SingleAccountResponse(accountResponseDto);
    }

}
