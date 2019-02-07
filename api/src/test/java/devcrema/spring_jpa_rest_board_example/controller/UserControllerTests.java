package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.ResponseMessage;
import devcrema.spring_jpa_rest_board_example.user.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
        ResponseEntity<ResponseMessage> maybeOk = userController.signUp(validRequest);
        ResponseEntity<ResponseMessage> maybeBadRequest = userController.signUp(null);
        ResponseEntity<ResponseMessage> maybeConflictAndDuplicatedEmail = userController.signUp(duplicatedEmailRequest);
        ResponseEntity<ResponseMessage> maybeConflictAndDuplicatedNickname = userController.signUp(duplicatedNicknameRequest);

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
