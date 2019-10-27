package devcrema.spring_jpa_rest_board_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.post.*;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Slf4j
public class PostControllerTests {

    @Autowired
    private UserFixtureGenerator userFixtureGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private static String oauthHeader;
    private static User user;

    @Autowired
    private PostFixtureGenerator postFixtureGenerator;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private SavePostService savePostService;

    @Before
    public void setUp() throws Exception{
        user = userFixtureGenerator.generateTestUserFixture();
        oauthHeader = AccessTokenUtil.getOauthHeaderValue(mockMvc, user.getUsername(), UserFixtureGenerator.PASSWORD);
    }

    @Test
    public void testGetPosts() throws Exception {
        //given
        String url = "/api/posts";
        List<GetPostResponse> postList = new ArrayList<>();
        postList.add(PostFixtureGenerator.buildTestPost(user));
        postList.add(PostFixtureGenerator.buildTestPost(user));
        given(postRepository.findAllByEnabledTrue()).willReturn(postList);
        //when, then
        MvcResult mvcResult = mockMvc.perform(get(url).header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<GetPostResponse> getPostRespons = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post[].class));

        assertThat(getPostRespons.isEmpty()).isFalse();
    }

    @Test
    public void testGetPost() throws Exception {
        //given
        String url = "/api/posts/1";
        given(postRepository.getById(1L)).willReturn(Optional.of(PostFixtureGenerator.buildTestPost(user)));
        //when, then
        MvcResult mvcResult = mockMvc.perform(get(url).header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        GetPostResponse post = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post.class);

        assertThat(post.getTitle()).isEqualTo(PostFixtureGenerator.buildTestPost(user).getTitle());
    }

    @Test
    public void testCreatePost() throws Exception {
        //given
        String url = "/api/posts";
        Post post = PostFixtureGenerator.buildTestPost(user);
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);
        //when, then
        //권한이 없는 경우
        mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        //성공
        mockMvc.perform(post(url)
                    .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testModifyPost() throws Exception {
        //given
        String url = "/api/posts/1";
        Post post = PostFixtureGenerator.buildTestPost(user);
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);

        //when, then
        //없는 게시물 요청한 경우
        given(postRepository.findById(Long.valueOf(2L))).willReturn(Optional.empty());
        mockMvc.perform(put("/api/posts/2")
                .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        //유저 자신이 만든 게시물이 아닌 경우
        given(postRepository.findById(Long.valueOf(2L))).willReturn(Optional.of(PostFixtureGenerator.buildTestPost(User.builder().id(20L).build())));
        mockMvc.perform(put("/api/posts/2")
                .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
        //성공
        given(postRepository.findById(Long.valueOf(1L))).willReturn(Optional.of(PostFixtureGenerator.buildTestPost(user)));
        mockMvc.perform(put(url)
                .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }


}
