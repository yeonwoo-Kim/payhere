package project.payhereprocess.business.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.domain.AccountBook;
import project.payhereprocess.persistence.accountbookRepository.AccountbookRepository;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountbookService {
    private final AccountbookRepository accountbookRepository;

    @Transactional
    public AccountResponseDto command(AccountCommand command) throws Exception{
        AccountBook newAccount = AccountBook.builder()
                .amount(command.getAmount())
                .memo(command.getMemo())
                .useDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .isDeleted("N").build();
        AccountBook savedAccount = accountbookRepository.save(newAccount);
        return new AccountResponseDto("email", savedAccount.getAmount(), savedAccount.getMemo(), "가계부 내역이 저장되었습니다.");
    }
}
