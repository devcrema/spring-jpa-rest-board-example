package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.post.Comment;
import devcrema.spring_jpa_rest_board_example.post.Post;
import devcrema.spring_jpa_rest_board_example.post.PostRepository;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
@RequiredArgsConstructor
public class PostFixtureGenerator {

    private final PostRepository postRepository;
    private final CommentFixtureGenerator commentFixtureGenerator;

    public Post generateTestPostFixture(User user) {

        List<Post> posts = postRepository.findAll();

        if(posts.isEmpty()){
            Post post = postRepository.save(buildTestPost(user));
            Comment comment = commentFixtureGenerator.generate(user);
            Comment comment2 = commentFixtureGenerator.generate(user);
            post.addComment(comment);
            post.addComment(comment2);
            posts.add(post);
        }

        return posts.get(posts.size()-1);
    }

    public static Post buildTestPost(User user){
        return Post.builder()
                .title("TestTitle")
                .content("TestContent")
                .user(user)
                .build();
    }

}
