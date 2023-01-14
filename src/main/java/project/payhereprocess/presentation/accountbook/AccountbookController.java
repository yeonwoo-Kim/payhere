package project.payhereprocess.presentation.accountbook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.payhereprocess.business.accountbook.AccountbookService;
import project.payhereprocess.business.accountbook.command.AccountCommand;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;
import project.payhereprocess.presentation.accountbook.dto.AccountResponseDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountbookController {
    private final AccountbookService accountbookService;

    @PostMapping("/account/add")
    public ResponseEntity<AccountResponseDto> insert(@RequestBody AccountRequestDto dto) throws Exception {
        AccountCommand command = dto.toCommand();
        AccountResponseDto response = accountbookService.command(command);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
