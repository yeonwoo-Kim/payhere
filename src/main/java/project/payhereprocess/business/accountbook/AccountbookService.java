package project.payhereprocess.business.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.domain.AccountBook;
import project.payhereprocess.domain.User;
import project.payhereprocess.persistence.accountbookRepository.AccountbookRepository;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;
import project.payhereprocess.presentation.accountbook.dto.GetAllAccountBookResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountbookService {
    private final AccountbookRepository accountbookRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountResponseDto command(AccountCommand command) throws Exception {
        AccountBook newAccount = AccountBook.builder()
                .amount(command.getAmount())
                .memo(command.getMemo())
                .useDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .isDeleted("N").build();
        AccountBook savedAccount = accountbookRepository.save(newAccount);
        return new AccountResponseDto("email", savedAccount.getAmount(), savedAccount.getMemo(), "가계부 내역이 저장되었습니다.");
    }

    @Transactional
    public List<GetAllAccountBookResponseDto> getAllAccount(String email) {
        // 사용자가 존재하는지 확인
        User savedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(email));
        // 사용자 id로 account book 조회
        List<AccountBook> savedAccountList = accountbookRepository.findAllByUserId(savedUser.getId());

        // account boot entity list를 response dto list로 변환
        return savedAccountList.stream().map(accountBook -> GetAllAccountBookResponseDto.from(accountBook)).toList();
    }
}