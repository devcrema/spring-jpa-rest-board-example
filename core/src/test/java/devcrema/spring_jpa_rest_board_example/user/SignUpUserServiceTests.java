package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class SignUpUserServiceTests {
    private final SignUpUserService signUpUserService;
    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;
    private final ModelMapper modelMapper;

    private final UserFixtureGenerator userFixtureGenerator;

    @Test
    public void testSignUp() {
        //given
        String password = "testPassword";
        String nickname =  "first user";
        User signUpRequest = new SignUpUserRequest("test-email@email.email"
                , nickname
                , password)
                .toUser(modelMapper);
        long userCount = userRepository.count();

        //when
        signUpUserService.signUp(signUpRequest);

        //then
        assertThat(userRepository.count()).isEqualTo(userCount + 1);
        assertThat(userRepository.findByEmail(signUpRequest.getEmail()))
                .isPresent()
                .hasValueSatisfying((it)->{
                    assertThat(userPasswordEncoder.matches(password,it.getPassword())).isTrue();
                    assertThat(it.getNickname()).isEqualTo(nickname);
                });
    }

    @Test
    public void testDuplicatedEmailException() {
        //given
        User generatedUser = userFixtureGenerator.generate();
        User duplicatedEmailRequest = new SignUpUserRequest(generatedUser.getEmail()
                , "second user"
                , "password")
                .toUser(modelMapper);
        //when, then
        assertThatThrownBy(() -> signUpUserService.signUp(duplicatedEmailRequest))
                .isInstanceOf(DuplicatedEmailException.class);
    }


    public void testDuplicatedNicknameException(){
        //TODO
    }

}
