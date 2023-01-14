package project.payhereprocess.business.accountbook.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AccountCommand {
    private final Long amount;
    private final String memo;
    private final String email;
}
