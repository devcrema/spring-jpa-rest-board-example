package devcrema.spring_jpa_rest_board_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.AccessTokenUtil;
import devcrema.spring_jpa_rest_board_example.CustomTestConfiguration;
import devcrema.spring_jpa_rest_board_example.post.GetPostResponse;
import devcrema.spring_jpa_rest_board_example.post.Post;
import devcrema.spring_jpa_rest_board_example.post.PostRepository;
import devcrema.spring_jpa_rest_board_example.post.SavePostRequest;
import devcrema.spring_jpa_rest_board_example.test_fixture.PostFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomTestConfiguration.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
public class PostControllerTests {

    private final UserFixtureGenerator userFixtureGenerator;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    private static String oauthHeader;
    private static User user;

    private final PostRepository postRepository;

    @BeforeEach
    public void setUp() throws Exception{
        user = userFixtureGenerator.generateTestUserFixture();
        oauthHeader = AccessTokenUtil.getOauthHeaderValue(mockMvc, user.getUsername(), UserFixtureGenerator.PASSWORD);
    }

    @Test
    public void testGetPosts() throws Exception {
        //given
        String url = "/api/posts";
        List<GetPostResponse> postList = new ArrayList<>();
        postRepository.save(PostFixtureGenerator.buildTestPost(user));
        postRepository.save(PostFixtureGenerator.buildTestPost(user));
        //when, then
        mockMvc.perform(get(url).header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetPost() throws Exception {
        //given
        String url = "/api/posts/1";

        Post post = postRepository.save(PostFixtureGenerator.buildTestPost(user));

        //when, then
        mockMvc.perform(get(url).header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", is(post.getTitle())));
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
        String url = "/api/posts/";
        Post post = postRepository.save(PostFixtureGenerator.buildTestPost(user));
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);

        //when, then
        //없는 게시물 요청한 경우
        mockMvc.perform(put("/api/posts/-1")
                .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //성공
        mockMvc.perform(put(url+post.getId())
                .header(AccessTokenUtil.AUTHORIZATION_KEY, oauthHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }


}
