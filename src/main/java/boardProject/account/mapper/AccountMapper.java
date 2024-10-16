package boardProject.account.mapper;

import boardProject.account.dto.AccountResponseDto;
import boardProject.account.dto.AccountSignUpDto;
import boardProject.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountSignUpDtoToAccount (AccountSignUpDto accountSignUpDto);

    AccountResponseDto accountToAccountResponseDto (Account account);
}
