package project.payhereprocess.presentation.user.dto;

import lombok.Data;
import project.payhereprocess.business.user.command.UserCommand;

import javax.validation.constraints.Email;

@Data
public class SignupRequestDto {
    @Email
    private String email;
    private String password;

    public UserCommand toCommand() {
        return new UserCommand(email, password);
    }
}
