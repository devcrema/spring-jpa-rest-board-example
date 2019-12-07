package devcrema.spring_jpa_rest_board_example.integration;

import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public class OauthIntegrationTests {

    private final UserFixtureGenerator userFixtureGenerator;
    private final MockMvc mockMvc;

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
