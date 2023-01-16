package project.payhereprocess.presentation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.payhereprocess.business.user.UserService;
import project.payhereprocess.business.user.command.UserCommand;
import project.payhereprocess.jwt.JwtFilter;
import project.payhereprocess.jwt.TokenProvider;
import project.payhereprocess.presentation.user.dto.LoginRequestDto;
import project.payhereprocess.presentation.user.dto.SignupRequestDto;
import project.payhereprocess.presentation.user.dto.TokenDto;
import project.payhereprocess.presentation.user.dto.UserResponseDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    /**
     * 로그인
     * @param loginDto
     * @return
     */
    @PostMapping("/user/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequestDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
