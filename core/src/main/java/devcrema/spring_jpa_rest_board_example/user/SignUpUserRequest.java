package devcrema.spring_jpa_rest_board_example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignUpUserRequest {
    @Email(message = "email 형식이 맞지 않습니다.")
    private String email;
    @Size(min = 1, max = 20, message = "닉네임은 1 ~ 20 글자 중에 입력하셔야합니다.")
    private String nickname;
    @Size(min = 6, max = 30, message = "비밀번호는 6 ~ 30 글자 중에 입력하셔야합니다.")
    private String password;

    public User toUser(ModelMapper modelMapper){
        return modelMapper.map(this, User.class);
    }
}
