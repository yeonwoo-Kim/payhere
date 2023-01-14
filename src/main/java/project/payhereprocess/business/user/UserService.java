package project.payhereprocess.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.command.UserCommand;
import project.payhereprocess.business.command.UserResponseDto;
import project.payhereprocess.domain.User;
import project.payhereprocess.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto command(UserCommand command) {
        // 가입된 회원인지 체크
        Boolean isExists = userRepository.existsByEmail(command.getEmail());
        if (isExists) throw new RuntimeException(command.getEmail());

        User newUser = User
                .builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .build();
        User savedUser = userRepository.save(newUser);
        return new UserResponseDto(savedUser.getEmail(), "회원가입이 완료되었습니다.");
    }
}