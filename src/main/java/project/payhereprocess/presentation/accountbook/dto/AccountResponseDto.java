package project.payhereprocess.presentation.accountbook.dto;

import lombok.Data;

@Data
public class AccountResponseDto {
    private final Long amount;
    private final String memo;
    private final String responseMessage;
}
