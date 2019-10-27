package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@ActiveProfiles(profiles = "test")
@Transactional
@Slf4j
public class SavePostServiceTests {

    @Autowired
    private SavePostService savePostService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserFixtureGenerator userFixtureGenerator;

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
