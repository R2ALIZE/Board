package boardProject.domain.account.mapper;

import boardProject.domain.account.dto.AccountResponseDto;
import boardProject.domain.account.dto.AccountSignUpDto;
import boardProject.domain.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountSignUpDtoToAccount (AccountSignUpDto accountSignUpDto);

    AccountResponseDto accountToAccountResponseDto (Account account);
}
