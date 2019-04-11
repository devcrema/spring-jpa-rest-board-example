package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
    @Autowired
    private PostRepository postRepository;

    private static User user;

    @Before
    public void setUp(){
        user = UserFixtureGenerator.generateTestUserFixture(userRepository, userPasswordEncoder);
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
