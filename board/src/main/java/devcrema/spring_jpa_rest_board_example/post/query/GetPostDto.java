package devcrema.spring_jpa_rest_board_example.post.query;

import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.Value;

//DTO projection 연습
@Value
public class GetPostDto {
    Long id;
    String title;
    String content;
    User user;
}
