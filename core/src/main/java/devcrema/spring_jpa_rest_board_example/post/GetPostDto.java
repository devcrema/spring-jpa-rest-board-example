package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.Value;

@Value
public class GetPostDto {
    Long id;
    String title;
    String content;
    User user;
}
