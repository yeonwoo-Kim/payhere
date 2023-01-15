package project.payhereprocess.business.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.domain.AccountBook;
import project.payhereprocess.domain.User;
import project.payhereprocess.persistence.accountbook.AccountBookRepository;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseMessageDto;
import project.payhereprocess.presentation.accountbook.dto.AccountUpdateRequestDto;
import project.payhereprocess.presentation.accountbook.dto.GetAllAccountBookResponseDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookService {
    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountResponseDto registerAccountBook(AccountCommand command) {
        User findUser = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new RuntimeException(String.valueOf(command.getUserId())));

        AccountBook newAccount = AccountBook.builder()
                .amount(command.getAmount())
                .memo(command.getMemo())
                .user(findUser)
                .build();
        AccountBook savedAccount = accountBookRepository.save(newAccount);
        return new AccountResponseDto("email", savedAccount.getAmount(), savedAccount.getMemo(), "가계부 내역이 저장되었습니다.");
    }

    @Transactional
    public List<GetAllAccountBookResponseDto> getAllAccount(Long userId) {
        // 사용자가 존재하는지 확인
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.valueOf(userId)));

        // 사용자 id로 account book 조회
        List<AccountBook> savedAccountList = accountBookRepository.findAllByUserId(findUser.getId());

        // account boot entity list를 response dto list로 변환
        return savedAccountList.stream().map(accountBook -> GetAllAccountBookResponseDto.from(accountBook)).toList();
    }

    @Transactional
    public AccountResponseDto updateAccountBook(Long id, AccountUpdateRequestDto dto) {
        // 해당하는 Account 내역 조회
        AccountBook accountBookForUpdate = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));

        if (accountBookForUpdate.getIsDeleted()) {
            new RuntimeException(String.valueOf(id));
        } else accountBookForUpdate.update(dto);

        return new AccountResponseDto(accountBookForUpdate.getUser().getEmail(), accountBookForUpdate.getAmount(), accountBookForUpdate.getMemo(), "정상적으로 수정되었습니다.");
    }

    @Transactional
    public AccountResponseMessageDto deleteAccountBook(Long id) {
        // 해당하는 Account 내역 조회
        AccountBook accountBookForDelete = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));


        if (accountBookForDelete.getIsDeleted()) {
            return new AccountResponseMessageDto(id, "이미 삭제된 내역입니다.");
        } else {
            accountBookForDelete.delete();
            return new AccountResponseMessageDto(id, "정상적으로 삭제되었습니다.");
        }
    }

    @Transactional
    public AccountResponseMessageDto restoreAccountBook(Long id) {
        // 해당하는 Account 내역 조회
        AccountBook accountBookForRestore = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));

        if (accountBookForRestore.getIsDeleted()) {
            accountBookForRestore.restore();
            return new AccountResponseMessageDto(id, "정상적으로 복구되었습니다.");
        } else {
            return new AccountResponseMessageDto(id, "이미 존재하는 내역입니다.");
        }
    }

    @Transactional
    public
}
