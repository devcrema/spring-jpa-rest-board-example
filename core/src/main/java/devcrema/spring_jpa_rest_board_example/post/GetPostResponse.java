package devcrema.spring_jpa_rest_board_example.post;

import java.util.List;

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
