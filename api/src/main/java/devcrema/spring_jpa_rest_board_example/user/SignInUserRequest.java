package devcrema.spring_jpa_rest_board_example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserRequest {
    private String email;
    private String password;
}
