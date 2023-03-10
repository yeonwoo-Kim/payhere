package project.payhereprocess.business.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.domain.AccountBook;
import project.payhereprocess.domain.User;
import project.payhereprocess.exception.BusinessException;
import project.payhereprocess.exception.ErrorCode;
import project.payhereprocess.jwt.TokenProvider;
import project.payhereprocess.persistence.accountbook.AccountBookRepository;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.accountbook.dto.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBookService {
    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    @Transactional
    public AccountResponseDto registerAccountBook(AccountCommand command, String email) {
        User findUser = authorizedUser(email);
        AccountBook newAccount = AccountBook.builder()
                .amount(command.getAmount())
                .memo(command.getMemo())
                .user(findUser)
                .build();
        AccountBook savedAccount = accountBookRepository.save(newAccount);
        return new AccountResponseDto(savedAccount.getAmount(), savedAccount.getMemo(), "가계부 내역이 저장되었습니다.");
    }

    @Transactional
    public List<GetAllAccountBookResponseDto> getAllAccount(String email) {

        User findUser = authorizedUser(email);

        // 사용자 id로 account book 조회
        List<AccountBook> savedAccountList = accountBookRepository.findAllByUserId(findUser.getId());

        // account boot entity list를 response dto list로 변환
        return savedAccountList.stream().map(accountBook -> GetAllAccountBookResponseDto.from(accountBook)).collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDto updateAccountBook(Long id, AccountUpdateRequestDto dto, String email) {
        User findUser = authorizedUser(email);
        AccountBook accountBookForUpdate = accountBookRepository.findByIdAndUserId(id, findUser.getId())
                .orElseThrow(() -> new BusinessException("리소스에 대한 접근 권한이 없습니다.", ErrorCode.NOT_AUTHORITY));

        if (accountBookForUpdate.getIsDeleted()) {
            new BusinessException("이미 삭제된 내역입니다.", ErrorCode.IS_DELETED);
        } else accountBookForUpdate.update(dto);

        return new AccountResponseDto(accountBookForUpdate.getAmount(), accountBookForUpdate.getMemo(), "정상적으로 수정되었습니다.");
    }

    @Transactional
    public AccountResponseMessageDto deleteAccountBook(Long id, String email) {
        User findUser = authorizedUser(email);

        AccountBook accountBookForDelete = accountBookRepository.findByIdAndUserId(id, findUser.getId())
                .orElseThrow(() -> new BusinessException("리소스에 대한 접근 권한이 없습니다.", ErrorCode.NOT_AUTHORITY));


        if (accountBookForDelete.getIsDeleted()) {
            return new AccountResponseMessageDto(id, "이미 삭제된 내역입니다.");
        } else {
            accountBookForDelete.delete();
            return new AccountResponseMessageDto(id, "정상적으로 삭제되었습니다.");
        }
    }

    @Transactional
    public AccountResponseMessageDto restoreAccountBook(Long id, String email) {
        User findUser = authorizedUser(email);

        AccountBook accountBookForRestore = accountBookRepository.findByIdAndUserId(id, findUser.getId())
                .orElseThrow(() -> new BusinessException("리소스에 대한 접근 권한이 없습니다.", ErrorCode.NOT_AUTHORITY));

        if (accountBookForRestore.getIsDeleted()) {
            accountBookForRestore.restore();
            return new AccountResponseMessageDto(id, "정상적으로 복구되었습니다.");
        } else {
            return new AccountResponseMessageDto(id, "이미 존재하는 내역입니다.");
        }
    }

    @Transactional(readOnly = true)
    public AccountDetailResponseDto detailAccountBook(Long id, String email) {
        User findUser = authorizedUser(email);
        AccountBook detailAccountBook = accountBookRepository.findByIdAndUserId(id, findUser.getId())
                .orElseThrow(() -> new BusinessException("리소스에 대한 접근 권한이 없습니다.", ErrorCode.NOT_AUTHORITY));

        AccountDetailResponseDto response = AccountDetailResponseDto.from(detailAccountBook);
        return response;
    }

    private User authorizedUser(String email) {
        // 인증된 사용자의 User 확인
        User findUser = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new BusinessException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND));
        return findUser;
    }
}
