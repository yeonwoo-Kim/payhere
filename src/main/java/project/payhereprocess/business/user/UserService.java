package project.payhereprocess.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.payhereprocess.business.user.command.UserCommand;
import project.payhereprocess.domain.User;
import project.payhereprocess.jwt.TokenProvider;
import project.payhereprocess.persistence.user.UserRepository;
import project.payhereprocess.presentation.user.dto.LoginRequestDto;
import project.payhereprocess.presentation.user.dto.TokenDto;
import project.payhereprocess.presentation.user.dto.UserResponseDto;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
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

    @Transactional
    public TokenDto login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.createToken(authentication);
        return tokenDto;
    }
}
