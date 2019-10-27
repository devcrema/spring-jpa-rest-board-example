package devcrema.spring_jpa_rest_board_example.integration;

import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Slf4j
public class OauthIntegrationTests {
    @Autowired
    private UserFixtureGenerator userFixtureGenerator;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetOauthToken() throws Exception{
        //given
        User user = userFixtureGenerator.generateTestUserFixture();
        //when, then
        String accessToken = AccessTokenUtil.getAccessToken(mockMvc, user.getUsername(), UserFixtureGenerator.PASSWORD);
        log.info(accessToken);
        assertThat(accessToken).isNotBlank();
    }

}
