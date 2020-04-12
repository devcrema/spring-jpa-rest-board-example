package devcrema.spring_jpa_rest_board_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserRequest;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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

    private static final String EMAIL = "email@unexistent-domain.unexistent";
    private static final String NICKNAME = "first user";
    private static final String PASSWORD = "testPassword";

    private static final String URL = "/api/users";

    @Test
    public void signUpWithValidRequestThenReturn200() throws Exception {
        //given
        SignUpUserRequest validRequest = new SignUpUserRequest(EMAIL, NICKNAME, PASSWORD);

        //when,then
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void signUpWithInvalidEmailThenReturn400() throws Exception {
        //given
        SignUpUserRequest invalidRequest = new SignUpUserRequest("notEmail", NICKNAME, PASSWORD);
        //when, then
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUpWithInvalidNicknameThenReturn400() throws Exception {
        //given
        SignUpUserRequest invalidRequest = new SignUpUserRequest(EMAIL, " ", PASSWORD);
        //when, then
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUpWithInvalidPasswordThenReturn400() throws Exception {
        //given
        SignUpUserRequest invalidRequest = new SignUpUserRequest(EMAIL, NICKNAME, "1");
        //when, then
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUpWithNullValueRequestThenReturn400() throws Exception {
        //given
        SignUpUserRequest invalidRequest = new SignUpUserRequest(null, NICKNAME, PASSWORD);
        //when, then
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
