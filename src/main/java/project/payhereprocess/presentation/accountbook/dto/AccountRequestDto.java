package project.payhereprocess.presentation.accountbook.dto;

import lombok.Data;
import project.payhereprocess.business.accountbook.command.AccountCommand;

@Data
public class AccountRequestDto {
    private Long amount;
    private String memo;

    public AccountCommand toCommand() {
        return new AccountCommand(amount, memo);
    }
}
