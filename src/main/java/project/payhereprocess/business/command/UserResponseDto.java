package project.payhereprocess.business.command;

import lombok.Data;

@Data
public class UserResponseDto {
    private final String email;
    private final String responseMessage;
}
