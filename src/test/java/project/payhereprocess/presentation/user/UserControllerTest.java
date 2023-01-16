package project.payhereprocess.presentation.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import project.payhereprocess.exception.BusinessException;
import project.payhereprocess.presentation.user.dto.SignupRequestDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 API 성공")
    void signup_success() throws Exception {
        // given
        SignupRequestDto notDuplicatedEmailDto = new SignupRequestDto("any@any.com", "password");

        // when
        ResultActions result = postRequest("/user/signup", notDuplicatedEmailDto);

        // then
        result
                .andExpectAll(
                        status().isOk(),
                        jsonPath("email").value(notDuplicatedEmailDto.getEmail())
                );
    }

    @Test
    @DisplayName("회원가입 API 실패, email이 중복됨")
    void signup_fail_duplicated_email() throws Exception {
        // given
        SignupRequestDto duplicatedEmailDto = new SignupRequestDto("any@any.com", "password");
        postRequest("/user/signup", duplicatedEmailDto);

        // when
        AbstractThrowableAssert<?, ? extends Throwable> expectedException = assertThatThrownBy(
                () -> postRequest("/user/signup", duplicatedEmailDto)
        );

        // then
        expectedException.getCause().isInstanceOf(BusinessException.class);
    }

    private ResultActions postRequest(String requestUrl, Object content) throws Exception {
        return mvc.perform(
                post(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(content))
        );
    }
}