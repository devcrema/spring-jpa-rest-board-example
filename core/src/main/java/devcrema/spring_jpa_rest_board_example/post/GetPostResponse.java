package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.user.User;

import java.util.List;

public interface GetPostResponse {
    Long getId();
    String getTitle();
    String getContent();
    User getUser();
    List<Comment> getComments();
}
