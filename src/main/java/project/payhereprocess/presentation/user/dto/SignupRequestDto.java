package project.payhereprocess.presentation.user.dto;

import lombok.Data;
import project.payhereprocess.business.user.command.UserCommand;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignupRequestDto {
    @Email
    @NotNull(message = "이메일을 입력해주세요")
    private String email;
    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    public UserCommand toCommand() {
        return new UserCommand(email, password);
    }
}
