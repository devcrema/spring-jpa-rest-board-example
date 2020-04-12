package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.config.JwtTokenProvider;
import devcrema.spring_jpa_rest_board_example.exception.ResourceNotFoundException;
import devcrema.spring_jpa_rest_board_example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/sign-in")
@RequiredArgsConstructor
public class SignInUserController {

    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse signIn(@RequestBody SignInUserRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(ResourceNotFoundException::new);
        if(!userPasswordEncoder.matches(request.getPassword(), user.getPassword())) throw new ResourceNotFoundException();
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }
}
