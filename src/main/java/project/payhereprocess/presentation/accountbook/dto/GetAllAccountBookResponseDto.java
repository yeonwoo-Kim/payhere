package project.payhereprocess.presentation.accountbook.dto;

import lombok.Builder;
import lombok.Data;
import project.payhereprocess.domain.AccountBook;

import java.time.LocalDateTime;

@Data
@Builder
public class GetAllAccountBookResponseDto {
    private final Long id;
    private final Long amount;
    private final String memo;
    private final LocalDateTime useDate;
    private final LocalDateTime updateDate;
    private final Boolean isDeleted;

    public static GetAllAccountBookResponseDto from(AccountBook accountBook) {
        return GetAllAccountBookResponseDto.builder()
                .id(accountBook.getId())
                .amount(accountBook.getAmount())
                .memo(accountBook.getMemo())
                .useDate(accountBook.getUseDate())
                .updateDate(accountBook.getUpdateDate())
                .isDeleted(accountBook.getIsDeleted())
                .build();
    }
}
