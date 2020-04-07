package devcrema.spring_jpa_rest_board_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserRequest;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
public class UserControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private SignUpUserService signUpUserService;

    @Test
    public void testSignUp() throws Exception {
        //TODO 테스트코드 목적에 따라 나누기
        //given
        String url = "/api/users";

        String email = "email@unexistent-domain.unexistent";
        String nickname = "first user";
        String password = "testPassword";
        SignUpUserRequest validRequest = new SignUpUserRequest(email, nickname, password);
        SignUpUserRequest invalidRequest = new SignUpUserRequest("notEmail", nickname, password);
        SignUpUserRequest invalidRequest2 = new SignUpUserRequest(email, " ", " ");
        SignUpUserRequest invalidRequest3 = new SignUpUserRequest(null, nickname, password);

        //when,then
        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest2)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest3)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
}
