package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceTests {

    private final UserService userService;

    private final UserFixtureGenerator userFixtureGenerator;

    @BeforeEach
    public void setUp(){
        userFixtureGenerator.generateTestUserFixture();
    }

    @Test
    public void testLoadUserByUsername(){
        //given
        String email = UserFixtureGenerator.EMAIL;

        //when
        User user = (User) userService.loadUserByUsername(email);

        //then
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenLoadUnExistsUserThrowUsernameNotFoundException() {
        //given
        String unExistentEmail = "thisIsNotEmail@NotEmail.Not";

        //when, then
        assertThatThrownBy(()->userService.loadUserByUsername(unExistentEmail))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
