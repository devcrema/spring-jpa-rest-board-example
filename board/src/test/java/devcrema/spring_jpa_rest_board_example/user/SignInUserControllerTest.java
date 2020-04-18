package devcrema.spring_jpa_rest_board_example.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import devcrema.spring_jpa_rest_board_example.user.presentation.SignInUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
class SignInUserControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserFixtureGenerator userFixtureGenerator;

    @Test
    public void testSignIn() throws Exception {
        //given
        User user = userFixtureGenerator.generate();
        SignInUserRequest signInUserRequest = new SignInUserRequest(user.getEmail(), UserFixtureGenerator.PASSWORD);
        //when, then
        mockMvc.perform(post("/api/users/sign-in")
                .content(objectMapper.writeValueAsString(signInUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(blankOrNullString())));
    }

    @Test
    public void whenEmailDoesNotExists() throws Exception {
        //given
        userFixtureGenerator.generate();
        SignInUserRequest signInUserRequest = new SignInUserRequest("null@email.email", UserFixtureGenerator.PASSWORD);
        //when, then
        mockMvc.perform(post("/api/users/sign-in")
                .content(objectMapper.writeValueAsString(signInUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPasswordNotMatch() throws Exception {
        //given
        User user = userFixtureGenerator.generate();
        SignInUserRequest signInUserRequest = new SignInUserRequest(user.getEmail(), user.getPassword());
        //when, then
        mockMvc.perform(post("/api/users/sign-in")
                .content(objectMapper.writeValueAsString(signInUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}