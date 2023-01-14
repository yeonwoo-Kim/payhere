package project.payhereprocess.business.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.domain.AccountBook;
import project.payhereprocess.domain.User;
import project.payhereprocess.persistence.accountbook.AccountbookRepository;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;
import project.payhereprocess.presentation.accountbook.dto.GetAllAccountBookResponseDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookService {
    private final AccountbookRepository accountbookRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountResponseDto registerAccountBook(AccountCommand command) {
        User findUser = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new RuntimeException(command.getEmail()));

        AccountBook newAccount = AccountBook.builder()
                .amount(command.getAmount())
                .memo(command.getMemo())
                .user(findUser)
                .build();
        AccountBook savedAccount = accountbookRepository.save(newAccount);
        return new AccountResponseDto("email", savedAccount.getAmount(), savedAccount.getMemo(), "가계부 내역이 저장되었습니다.");
    }

    @Transactional
    public List<GetAllAccountBookResponseDto> getAllAccount(String email) {
        // 사용자가 존재하는지 확인
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(email));
        // 사용자 id로 account book 조회
        List<AccountBook> savedAccountList = accountbookRepository.findAllByUserId(findUser.getId());

        // account boot entity list를 response dto list로 변환
        return savedAccountList.stream().map(accountBook -> GetAllAccountBookResponseDto.from(accountBook)).toList();
    }
}
