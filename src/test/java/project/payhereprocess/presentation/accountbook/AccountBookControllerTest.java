package project.payhereprocess.presentation.accountbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import project.payhereprocess.jwt.TokenProvider;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountBookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @Test
    @DisplayName("가계부 내역 등록")
    void account_register_success() throws Exception {
        // given
        AccountRequestDto addAccountBook = new AccountRequestDto(1500L, "memo");
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = postRequest("/account/add", addAccountBook, token);
        // then
        result.andExpectAll(
                status().isCreated()
        );
    }

    @Test
    @DisplayName("가계부 내역 등록 실패, 값 입력하지 않음")
    void account_register_fail() throws Exception {
        // given
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = postRequest("/account/add", new AccountRequestDto(), token);

        // then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("가계부 내역 수정")
    void account_update_success() throws Exception {
        // given
        AccountRequestDto addAccountBook = new AccountRequestDto(3000L, "rewrite");
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = postRequest("/account/1/edit", addAccountBook, token);

        // then
        result.andExpectAll(
                status().isOk()
        );
    }

    @Test
    @DisplayName("가계부 내역 상세 조회")
    void account_show_success() throws Exception {
        // given
        Long id = 1L;
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = getRequest("/account/show/1", token);

        // then
        result.andExpectAll(
                status().isOk()
        );
    }

    @Test
    @DisplayName("가계부 내역 상세 조회 실패, 리소스 접근 권한 없음")
    void account_show_fail() throws Exception {
        // given
        Long id = 1L;
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = getRequest("/account/show/5", token);

        // then
        result.andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    @DisplayName("가계부 내역 전체조회")
    void account_all_show_success() throws Exception {
        // given
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = getRequest("/account/all", token);

        // then
        result.andExpectAll(
                status().isOk()
        );
    }

    @Test
    @DisplayName("가계부 내역 삭제")
    void account_delete_success() throws Exception {
        // given
        Long id = 1L;
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = getRequest("/account/delete", id, token);

        // then
        result.andExpectAll(
                status().isOk()
        );
    }

    @Test
    @DisplayName("가계부 내역 삭제건 복구")
    void account_restore_success() throws Exception {
        // given
        Long id = 1L;
        String token = accessToken("email@gmail.com", "password");

        // when
        ResultActions result = getRequest("/account/restore", id, token);

        // then
        result.andExpectAll(
                status().isOk()
        );
    }

    private ResultActions postRequest(String requestUrl, Object content, String accessToken) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        return mvc.perform(
                post(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(content))
                        .headers(httpHeaders)
        );
    }

    private ResultActions getRequest(String requestUrl, Long id, String accessToken) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        return mvc.perform(
                get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(id))
                        .characterEncoding("UTF-8")
                        .headers(httpHeaders)
        );
    }

    private ResultActions getRequest(String requestUrl, String accessToken) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        return mvc.perform(
                get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .headers(httpHeaders)
        );
    }

    private String accessToken(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }
}