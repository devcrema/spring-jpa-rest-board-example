package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.user.domain.User;
import devcrema.spring_jpa_rest_board_example.user.infrastructure.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
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
                    user.initialize(userPasswordEncoder.encode(user.getPassword()));
                    return userRepository.save(user);
                });
    }

    private User buildTestUser() {
        return User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(PASSWORD)
                .build();
    }
}
