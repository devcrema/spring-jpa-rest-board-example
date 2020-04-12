package devcrema.spring_jpa_rest_board_example.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/sign-up")
@RequiredArgsConstructor
public class SignUpUserController {

    private final SignUpUserService signUpUserService;

    private final ModelMapper modelMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody SignUpUserRequest request) {
        User mappedUser = modelMapper.map(request, User.class);
        signUpUserService.signUp(mappedUser);
    }
}
