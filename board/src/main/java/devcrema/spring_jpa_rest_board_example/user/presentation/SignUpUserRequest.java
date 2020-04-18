package devcrema.spring_jpa_rest_board_example.user.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserRequest {
    @NotNull(message = "이메일을 입력하셔야합니다.")
    @Email(message = "email 형식이 맞지 않습니다.")
    private String email;
    @NotNull(message = "닉네임을 입력하셔야합니다.")
    @NotBlank
    @Size(min = 1, max = 20, message = "닉네임은 1 ~ 20 글자 중에 입력하셔야합니다.")
    private String nickname;
    @NotNull(message = "비밀번호를 입력하셔야합니다.")
    @Size(min = 6, max = 30, message = "비밀번호는 6 ~ 30 글자 중에 입력하셔야합니다.")
    private String password;
}
