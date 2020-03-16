package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFixtureGenerator {

    public static final String EMAIL = "someone@some-domain.some";
    public static final String NICKNAME = "someone";
    public static final String PASSWORD = "123456789a";

    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;

    public User generate() {
        return userRepository.findByEmail(EMAIL)
                .orElseGet(() -> {
                    User user = buildTestUser();
                    user.initialize(userPasswordEncoder);
                    return userRepository.save(user);
                });
    }

    public static User buildTestUser() {
        return User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(PASSWORD)
                .build();
    }
}
