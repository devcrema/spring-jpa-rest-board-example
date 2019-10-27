package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@ActiveProfiles(profiles = "test")
@Transactional
@Slf4j
public class SignUpUserServiceTests {
    @Autowired
    private SignUpUserService signUpUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testSignUp(){
        //given
        String email = "email@unexistent-domain.unexistent";
        String nickname = "first user";
        String password = "testPassword";
        User validRequest = new SignUpUserRequest(email, nickname, password).toUser(modelMapper);
        User duplicatedEmailRequest = new SignUpUserRequest(email, "second user", password).toUser(modelMapper);
        User duplicatedNicknameRequest = new SignUpUserRequest("email2@unexistent-domain.unexistent", nickname, password).toUser(modelMapper);
        long userCount = userRepository.count();

        //when
        SignUpUserService.SignUpResult maybeSuccess = signUpUserService.signUp(validRequest);
        SignUpUserService.SignUpResult maybeEmailDuplicated = signUpUserService.signUp(duplicatedEmailRequest);
        SignUpUserService.SignUpResult maybeNicknameDuplicated = signUpUserService.signUp(duplicatedNicknameRequest);

        //then
        assertThat(maybeSuccess).isEqualTo(SignUpUserService.SignUpResult.SUCCESS);
        assertThat(maybeEmailDuplicated).isEqualTo(SignUpUserService.SignUpResult.DUPLICATED_EMAIL);
        assertThat(maybeNicknameDuplicated).isEqualTo(SignUpUserService.SignUpResult.DUPLICATED_NICKNAME);
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
