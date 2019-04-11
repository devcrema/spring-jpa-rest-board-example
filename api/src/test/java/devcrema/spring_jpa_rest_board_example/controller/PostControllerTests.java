package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.post.Post;
import devcrema.spring_jpa_rest_board_example.post.PostRepository;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@Slf4j
public class PostControllerTests {

    @Autowired
    private PostController postController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String oauthHeader;
    private static User user;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setUp() throws Exception{
        user = UserFixtureGenerator.generateTestUserFixture(userRepository, userPasswordEncoder);
        oauthHeader = AccessTokenUtil.getOauthHeaderValue(mockMvc, user.getUsername(), UserFixtureGenerator.PASSWORD);
    }

    @Test
    public void testGetPosts() throws Exception {
        //given
        String url = "/api/posts";
        List<Post> postList = new ArrayList<>();
        postList.add(PostFixtureGenerator.buildTestPost(user));
        postList.add(PostFixtureGenerator.buildTestPost(user));

        given(postRepository.findAll()).willReturn(postList);
        //when, then
        MvcResult mvcResult = mockMvc.perform(get(url).header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertThat(StringUtils.isBlank(mvcResult.getResponse().getContentAsString())).isFalse();
    }

}