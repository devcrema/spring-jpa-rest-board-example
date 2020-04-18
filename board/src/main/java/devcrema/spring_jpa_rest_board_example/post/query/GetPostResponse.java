package devcrema.spring_jpa_rest_board_example.post.query;

import devcrema.spring_jpa_rest_board_example.post.domain.Comment;

import java.util.List;

//interface projection 연습
public interface GetPostResponse {
    Long getId();
    String getTitle();
    String getContent();
    UserResponse getUser();
    List<Comment> getComments();

    interface UserResponse{
        Long getId();
    }
}
