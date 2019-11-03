package devcrema.spring_jpa_rest_board_example.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@ActiveProfiles(profiles = "test")
@Transactional
@Slf4j
public class PostRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostFixtureGenerator postFixtureGenerator;
    @Autowired
    UserFixtureGenerator userFixtureGenerator;
    @Autowired
    ObjectMapper objectMapper;

    private static User user;

    @Before
    public void setUp(){
        user = userFixtureGenerator.generateTestUserFixture();
    }

    @Test
    public void loadCommentWithoutN1Test() {
        //given
        Post post = postFixtureGenerator.generateTestPostFixture(user);

        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.clear();
        //when
        GetPostResponse savedPost = postRepository.getById(post.getId()).orElseThrow(RuntimeException::new);
        List<Comment> comments = savedPost.getComments();

        //then
        assertThat(statistics.getQueryExecutionCount()).isEqualTo(1L);
        assertThat(savedPost.getComments().size()).isEqualTo(2);
    }

    @Test
    public void projectionWithDtoTest() throws JsonProcessingException {
        //given
        Post post = postFixtureGenerator.generateTestPostFixture(user);
        //when
        GetPostDto savedPost = postRepository.getDtoById(post.getId()).orElseThrow(RuntimeException::new);
        GetPostResponse projectionResponse = postRepository.getById(post.getId()).orElseThrow(RuntimeException::new);
        //then
        assertThat(savedPost.getTitle()).isNotEmpty();
        assertThat(projectionResponse.getUser().getId()).isNotNull();
        log.info(objectMapper.writeValueAsString(savedPost));
        log.info(objectMapper.writeValueAsString(projectionResponse));
    }
}