package devcrema.spring_jpa_rest_board_example.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcrema.spring_jpa_rest_board_example.config.JwtTokenProvider;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static devcrema.spring_jpa_rest_board_example.config.JwtTokenProvider.TOKEN_HEADER_PREFIX;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
public class PostControllerTests {

    private final UserFixtureGenerator userFixtureGenerator;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private static String token;
    private static User user;

    private final PostRepository postRepository;

    private final static String URL = "/api/posts";

    @BeforeEach
    public void setUp() {
        user = userFixtureGenerator.generate();
        token = TOKEN_HEADER_PREFIX + jwtTokenProvider.createToken(String.valueOf(user.getId())
                , user.getRoles());
    }

    @Test
    public void testGetPosts() throws Exception {
        //given

        postRepository.save(PostFixtureGenerator.buildTestPost(user));
        postRepository.save(PostFixtureGenerator.buildTestPost(user));
        //when, then
        mockMvc.perform(get(URL).header(JwtTokenProvider.TOKEN_HEADER, token).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetPost() throws Exception {
        //given
        Post post = postRepository.save(PostFixtureGenerator.buildTestPost(user));

        //when, then
        mockMvc.perform(get(URL + "/1").header(JwtTokenProvider.TOKEN_HEADER, token).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", is(post.getTitle())));
    }

    @Test
    public void testCreatePost() throws Exception {
        //given
        Post post = PostFixtureGenerator.buildTestPost(user);
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);
        //when, then
        mockMvc.perform(post(URL)
                .header(JwtTokenProvider.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testCreatePostWithoutPermission() throws Exception {
        //given
        Post post = PostFixtureGenerator.buildTestPost(user);
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);
        //when, then
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testModifyPost() throws Exception {
        //given
        Post post = postRepository.save(PostFixtureGenerator.buildTestPost(user));
        SavePostRequest savePostRequest = modelMapper.map(post, SavePostRequest.class);

        //when, then
        //없는 게시물 요청한 경우
        mockMvc.perform(put("/api/posts/-1")
                .header(JwtTokenProvider.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //성공
        mockMvc.perform(put(URL + "/" + post.getId())
                .header(JwtTokenProvider.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savePostRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostValidation() throws Exception {
        //given
        SavePostRequest request = new SavePostRequest();
        request.setContent("");
        request.setTitle("");
        //when, then
        mockMvc.perform(post(URL)
                .header(JwtTokenProvider.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }
}
