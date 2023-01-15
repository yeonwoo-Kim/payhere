package project.payhereprocess.presentation.accountbook.dto;

import lombok.Data;

@Data
public class AccountUpdateRequestDto {
    private Long amount;
    private String memo;
}
