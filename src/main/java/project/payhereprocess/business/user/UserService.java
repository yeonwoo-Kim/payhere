package project.payhereprocess.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.user.command.UserCommand;
import project.payhereprocess.domain.User;
import project.payhereprocess.exception.BusinessException;
import project.payhereprocess.exception.ErrorCode;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.user.dto.UserResponseDto;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto command(UserCommand command) {
        // 가입된 회원인지 체크
        Boolean isExists = userRepository.existsByEmail(command.getEmail());
        if (isExists) throw new BusinessException("이미 존재하는 사용자입니다.", ErrorCode.DUPLICATION);

        User newUser = User
                .builder()
                .email(command.getEmail())
                .password(passwordEncoder.encode(command.getPassword()))
                .build();
        User savedUser = userRepository.save(newUser);
        return new UserResponseDto(savedUser.getEmail(), "회원가입이 완료되었습니다.");
    }
}
