package project.payhereprocess.business.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCommand {
    private final String email;
    private final String password;
}
