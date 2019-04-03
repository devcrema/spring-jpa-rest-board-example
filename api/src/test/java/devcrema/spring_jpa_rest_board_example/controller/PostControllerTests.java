package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@Slf4j
public class PostControllerTests {

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

    private String accessToken;

    @Before
    public void setUp() throws Exception{
        User user = UserFixtureGenerator.generateTestUserFixture(userRepository, userPasswordEncoder);
        accessToken = AccessTokenUtil.getAccessToken(mockMvc, user.getUsername(), UserFixtureGenerator.PASSWORD);
    }


}
