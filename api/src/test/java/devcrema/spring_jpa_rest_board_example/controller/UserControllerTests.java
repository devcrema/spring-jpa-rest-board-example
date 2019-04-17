package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserRequest;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserService;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Slf4j
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignUpUserService signUpUserService;

    @Before
    public void setUp() {
        given(signUpUserService.signUp(any())).willReturn(SignUpUserService.SignUpResult.SUCCESS);
    }

    @Test
    public void testSignUp() throws Exception {
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
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(validRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(invalidRequest2)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(invalidRequest3)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
}
