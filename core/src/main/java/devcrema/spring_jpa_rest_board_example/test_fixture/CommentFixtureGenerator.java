package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.post.Comment;
import devcrema.spring_jpa_rest_board_example.post.CommentRepository;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@RequiredArgsConstructor
public class CommentFixtureGenerator {
    private final CommentRepository commentRepository;

    public Comment generate(User user){
        return commentRepository.save(Comment.builder()
                .user(user)
                .content("this is comment!!").build());
    }
}
