package project.payhereprocess.presentation.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.payhereprocess.business.accountbook.AccountBookService;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;
import project.payhereprocess.presentation.accountbook.dto.AccountUpdateRequestDto;
import project.payhereprocess.presentation.accountbook.dto.GetAllAccountBookResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountbookController {
    private final AccountBookService accountbookService;

    @PostMapping("/account/add")
    public ResponseEntity<AccountResponseDto> insert(@RequestBody AccountRequestDto dto) {
        AccountCommand command = dto.toCommand();
        AccountResponseDto response = accountbookService.registerAccountBook(command);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/account/all/{userId}")
    public ResponseEntity<List<GetAllAccountBookResponseDto>> getAllAccountBook(@PathVariable Long userId) {
        List<GetAllAccountBookResponseDto> responseDtoList = accountbookService.getAllAccount(userId);

        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping("/account/{id}/edit")
    public ResponseEntity<AccountResponseDto> update(@PathVariable Long id, @RequestBody AccountUpdateRequestDto dto) {
        AccountResponseDto response = accountbookService.updateAccountBook(id, dto);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
