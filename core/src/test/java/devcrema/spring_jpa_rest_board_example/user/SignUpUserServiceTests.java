package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedEmailException;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedNicknameException;
import devcrema.spring_jpa_rest_board_example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class SignUpUserServiceTests {
    private final SignUpUserService signUpUserService;
    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;

    private final UserFixtureGenerator userFixtureGenerator;

    @Test
    public void testSignUp() {
        //given
        String password = "testPassword";
        String nickname = "first user";

        User signUpUser = User.builder()
                .email("test-email@email.email")
                .nickname(nickname)
                .password(password)
                .build();

        long userCount = userRepository.count();

        //when
        signUpUserService.signUp(signUpUser);

        //then
        assertThat(userRepository.count()).isEqualTo(userCount + 1);
        assertThat(userRepository.findByEmail(signUpUser.getEmail()))
                .isPresent()
                .hasValueSatisfying((it) -> {
                    assertThat(userPasswordEncoder.matches(password, it.getPassword())).isTrue();
                    assertThat(it.getNickname()).isEqualTo(nickname);
                });
    }

    @Test
    public void testDuplicatedEmailException() {
        //given
        User generatedUser = userFixtureGenerator.generate();
        User duplicatedEmailUser = User.builder()
                .email(generatedUser.getEmail())
                .nickname("second user")
                .password("password")
                .build();
        //when, then
        assertThatThrownBy(() -> signUpUserService.signUp(duplicatedEmailUser))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    public void testDuplicatedNicknameException() {
        //given
        User generatedUser = userFixtureGenerator.generate();
        User duplicatedNicknameUser = User.builder()
                .email("newemail@email.email")
                .nickname(generatedUser.getNickname())
                .password("password")
                .build();
        //when, then
        assertThatThrownBy(()-> signUpUserService.signUp(duplicatedNicknameUser))
                .isInstanceOf(DuplicatedNicknameException.class);
    }

}
