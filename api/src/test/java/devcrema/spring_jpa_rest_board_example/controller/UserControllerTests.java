package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.ResponseMessage;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserRequest;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Slf4j
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @MockBean
    private BindingResult bindingResult;

    @Before
    public void setUp(){
        //TODO mockMVC를 써서 테스트하는 것이 좋을지도
        given(bindingResult.hasErrors()).willReturn(false);
    }

    @Test
    public void testSignUp(){
        //given
        String email = "email@unexistent-domain.unexistent";
        String nickname = "first user";
        String password = "testPassword";
        SignUpUserRequest validRequest = new SignUpUserRequest(email, nickname, password);
        SignUpUserRequest duplicatedEmailRequest = new SignUpUserRequest(email, "second user", password);
        SignUpUserRequest duplicatedNicknameRequest = new SignUpUserRequest("email2@unexistent-domain.unexistent", nickname, password);
        long userCount = userRepository.count();

        //when
        ResponseEntity<ResponseMessage> maybeOk = userController.signUp(validRequest, bindingResult);
        ResponseEntity<ResponseMessage> maybeBadRequest = userController.signUp(null, bindingResult);
        ResponseEntity<ResponseMessage> maybeConflictAndDuplicatedEmail = userController.signUp(duplicatedEmailRequest, bindingResult);
        ResponseEntity<ResponseMessage> maybeConflictAndDuplicatedNickname = userController.signUp(duplicatedNicknameRequest, bindingResult);

        //then
        assertThat(maybeOk.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(maybeBadRequest.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(maybeConflictAndDuplicatedEmail.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(maybeConflictAndDuplicatedNickname.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(userRepository.count()).isEqualTo(userCount + 1);
        Optional<User> savedUser = userRepository.findByEmail(email);
        if(savedUser.isPresent()){
            assertThat(userPasswordEncoder.matches(password,savedUser.get().getPassword())).isTrue();
            assertThat(savedUser.get().getNickname()).isEqualTo(nickname);
        } else {
            fail("saved user is not found!!");
        }

    }
}
