package project.payhereprocess.presentation.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.payhereprocess.business.accountbook.AccountBookService;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.presentation.accountbook.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountBookController {
    private final AccountBookService accountbookService;

    /**
     * Header의 토큰 내의 유저 이메일 추출
     *
     * @return
     */
    private String userEmail() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser.getName();
    }

    /**
     * 가계부 등록
     *
     * @param dto
     * @return
     */
    @PostMapping("/account/add")
    public ResponseEntity<AccountResponseDto> insert(@RequestBody AccountRequestDto dto) {
        AccountCommand command = dto.toCommand();
        AccountResponseDto response = accountbookService.registerAccountBook(command, userEmail());
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    /**
     * 가계부 전체 내역 조회
     *
     * @return
     */
    @GetMapping("/account/all")
    public ResponseEntity<List<GetAllAccountBookResponseDto>> getAllAccountBook() {
        List<GetAllAccountBookResponseDto> responseDtoList = accountbookService.getAllAccount(userEmail());

        return ResponseEntity.ok(responseDtoList);
    }

    /**
     * 가계부 내역 수정
     *
     * @param id
     * @param dto
     * @return
     */
    @PostMapping("/account/{id}/edit")
    public ResponseEntity<AccountResponseDto> update(@PathVariable Long id, @RequestBody AccountUpdateRequestDto dto) {
        AccountResponseDto response = accountbookService.updateAccountBook(id, dto, userEmail());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 가계부 내역 삭제
     *
     * @param id
     * @return
     */
    @GetMapping("/account/delete")
    public ResponseEntity<AccountResponseMessageDto> delete(@RequestParam Long id) {
        AccountResponseMessageDto response = accountbookService.deleteAccountBook(id, userEmail());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 가계부 내역 삭제건 복구
     *
     * @param id
     * @return
     */
    @GetMapping("/account/restore")
    public ResponseEntity<AccountResponseMessageDto> restore(@RequestParam Long id) {
        AccountResponseMessageDto response = accountbookService.restoreAccountBook(id, userEmail());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 가계부 상세 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/account/show/{id}")
    public ResponseEntity<AccountDetailResponseDto> show(@PathVariable Long id) {
        AccountDetailResponseDto response = accountbookService.detailAccountBook(id, userEmail());
        return ResponseEntity.ok(response);
    }
}
