package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Before
    public void setUp(){
        UserFixtureGenerator.generateTestUserFixture(userRepository, userPasswordEncoder);
    }

    @Test
    public void testLoadUserByUsername(){
        //given
        String email = UserFixtureGenerator.EMAIL;
        String unExistentEmail = "unexistentemail@unexistent-domain.unexistent";
        boolean usernameNotFoundErrorOccurred = false;
        //when
        User user = (User) userService.loadUserByUsername(email);
        try{
            userService.loadUserByUsername(unExistentEmail);
        } catch (UsernameNotFoundException usernameNotFoundException){
            usernameNotFoundErrorOccurred = true;
        }

        //then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(usernameNotFoundErrorOccurred).isTrue();
    }
}
