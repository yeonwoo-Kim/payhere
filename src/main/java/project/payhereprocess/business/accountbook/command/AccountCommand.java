package project.payhereprocess.business.accountbook.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountCommand {
    private final Long amount;
    private final String memo;
    private final String email;
}
