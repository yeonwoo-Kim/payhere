package project.payhereprocess.presentation.accountbook.dto;

import lombok.Data;
import project.payhereprocess.business.accountbook.command.AccountCommand;

import javax.validation.constraints.Email;

@Data
public class AccountRequestDto {
    private Long amount;
    private String memo;
    @Email
    private Long userId;

    public AccountCommand toCommand() {
        return new AccountCommand(amount, memo, userId);
    }
}
