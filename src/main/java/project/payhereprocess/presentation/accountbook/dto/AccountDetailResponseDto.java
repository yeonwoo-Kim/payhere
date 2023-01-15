package project.payhereprocess.presentation.accountbook.dto;

import lombok.Builder;
import lombok.Data;
import project.payhereprocess.domain.AccountBook;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountDetailResponseDto {
    private final Long id;
    private final Long amount;
    private final String memo;
    private final Boolean isDeleted;
    private final LocalDateTime useDate;
    private final LocalDateTime updateDate;
    public static AccountDetailResponseDto from(AccountBook accountBook) {
        return AccountDetailResponseDto.builder()
                .id(accountBook.getId())
                .amount(accountBook.getAmount())
                .memo(accountBook.getMemo())
                .useDate(accountBook.getUseDate())
                .updateDate(accountBook.getUpdateDate())
                .isDeleted(accountBook.getIsDeleted())
                .build();
    }
}
