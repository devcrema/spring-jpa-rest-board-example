package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.user.GetUserProjection;

public interface GetPostProjection {
    long getId();
    String getTitle();
    String getContent();
    GetUserProjection getUser();
}
