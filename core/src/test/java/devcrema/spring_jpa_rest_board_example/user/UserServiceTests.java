package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Test
    public void testLoadUserByUsername() {
        //given
        User user = userFixtureGenerator.generate();

        //when
        UserDetails userDetails = userService.loadUserByUsername(Long.toString(user.getId()));

        //then
        String email = ((User) userDetails).getEmail();
        assertThat(email).isEqualTo(user.getEmail());
    }

    @Test
    public void whenLoadUnExistsUserThrowUsernameNotFoundException() {
        //given
        String unRegisteredUserId = "-1";

        //when, then
        assertThatThrownBy(() -> userService.loadUserByUsername(unRegisteredUserId))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
