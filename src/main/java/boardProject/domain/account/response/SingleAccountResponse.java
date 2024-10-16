package boardProject.domain.account.response;

import boardProject.domain.account.dto.AccountResponseDto;
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
