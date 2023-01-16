package project.payhereprocess.presentation.accountbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.payhereprocess.business.accountbook.command.AccountCommand;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    @NotNull(message = "금액을 입력해주세요.")
    private Long amount;
    private String memo;

    public AccountCommand toCommand() {
        return new AccountCommand(amount, memo);
    }
}
