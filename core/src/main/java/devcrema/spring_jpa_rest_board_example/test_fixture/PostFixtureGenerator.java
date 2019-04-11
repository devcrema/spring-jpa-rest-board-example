package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.post.Post;
import devcrema.spring_jpa_rest_board_example.post.PostRepository;
import devcrema.spring_jpa_rest_board_example.user.User;

import java.util.List;

public class PostFixtureGenerator {

    public static Post generateTestPostFixture(PostRepository postRepository, User user) {

        List<Post> posts = postRepository.findAll();

        if(posts.isEmpty()){
            posts.add(postRepository.save(buildTestPost(user)));
        }

        return posts.get(0);
    }

    public static Post buildTestPost(User user){
        return Post.builder()
                .title("TestTitle")
                .content("TestContent")
                .user(user)
                .build();
    }

}
