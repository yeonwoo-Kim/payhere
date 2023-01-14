package project.payhereprocess.presentation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.payhereprocess.business.command.UserCommand;
import project.payhereprocess.business.command.UserResponseDto;
import project.payhereprocess.business.user.UserService;
import project.payhereprocess.presentation.user.dto.SignupRequestDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     * @param dto
     * @return
     */
    @PostMapping("/user/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid SignupRequestDto dto) {
        UserCommand command = dto.toCommand();
        UserResponseDto response = userService.command(command);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


}
