package project.payhereprocess.presentation.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.payhereprocess.business.accountbook.AccountBookService;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;
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

    @GetMapping("/account/all/{email}")
    public ResponseEntity<List<GetAllAccountBookResponseDto>> getAllAccountBook(@PathVariable String email) {
        List<GetAllAccountBookResponseDto> responseDtoList = accountbookService.getAllAccount(email);

        return ResponseEntity.ok(responseDtoList);
    }
}
