package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Slf4j
public class SavePostServiceTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPasswordEncoder userPasswordEncoder;
    @Autowired
    private SavePostService savePostService;

}
