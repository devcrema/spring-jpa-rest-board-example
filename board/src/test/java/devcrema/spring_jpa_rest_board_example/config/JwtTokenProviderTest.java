package devcrema.spring_jpa_rest_board_example.config;

import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
class JwtTokenProviderTest {
    private final JwtTokenProvider jwtTokenProvider;

    private final UserFixtureGenerator userFixtureGenerator;

    @Test
    public void createJwtToken(){
        //given
        User generateUser = userFixtureGenerator.generate();
        //when
        String token = jwtTokenProvider.createToken(String.valueOf(generateUser.getId())
                , generateUser.getRoles());
        //then
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    public void wrongValidateToken(){
        //given
        String testToken = "aaaa.bbbb.cccc";
        //when
        boolean result = jwtTokenProvider.validateToken(testToken);
        //then
        assertThat(result).isFalse();
    }
}