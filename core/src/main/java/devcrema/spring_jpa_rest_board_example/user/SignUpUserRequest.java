package devcrema.spring_jpa_rest_board_example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
public class SignUpUserRequest {
    private String email;

    private String nickname;

    private String password;

    public User toUser(ModelMapper modelMapper){
        return modelMapper.map(this, User.class);
    }
}
