package project.payhereprocess.presentation.accountbook.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
public class AccountResponseDto {
    private final String email;
    private final Long amount;
    private final String memo;
    private final String responseMessage;
}
