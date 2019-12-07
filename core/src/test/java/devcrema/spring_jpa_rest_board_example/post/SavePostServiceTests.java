package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
public class SavePostServiceTests {

    private final SavePostService savePostService;
    private final PostRepository postRepository;
    private final UserFixtureGenerator userFixtureGenerator;

    private static User user;

    @BeforeEach
    public void setUp(){
        user = userFixtureGenerator.generateTestUserFixture();
    }

    @Test
    public void testSavePost(){
        //given
        Post newPost = PostFixtureGenerator.buildTestPost(user);
        long count = postRepository.count();
        //when
        savePostService.savePost(newPost);
        //then
        assertThat(postRepository.count()).isEqualTo(count+1);
    }
}
