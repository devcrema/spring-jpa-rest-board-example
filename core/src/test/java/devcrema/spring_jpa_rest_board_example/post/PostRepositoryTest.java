package devcrema.spring_jpa_rest_board_example.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
public class PostRepositoryTest {

    private final EntityManager entityManager;
    private final PostRepository postRepository;
    private final PostFixtureGenerator postFixtureGenerator;
    private final UserFixtureGenerator userFixtureGenerator;
    private final ObjectMapper objectMapper;

    private static User user;

    @BeforeEach
    public void setUp(){
        user = userFixtureGenerator.generate();
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
        savedPost.getComments();

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