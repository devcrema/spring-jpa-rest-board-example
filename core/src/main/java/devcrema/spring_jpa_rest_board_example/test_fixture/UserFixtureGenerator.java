package devcrema.spring_jpa_rest_board_example.test_fixture;

import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;

import java.util.Optional;

public class UserFixtureGenerator {

    public static final String EMAIL = "someone@some-domain.some";
    public static final String NICKNAME = "someone";
    public static final String PASSWORD = "123456789a";

    public static User generateTestUserFixture(UserRepository userRepository, UserPasswordEncoder passwordEncoder) {

        Optional<User> optionalUser = userRepository.findByEmail(EMAIL);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User user = buildTestUser();
            user.initialize(passwordEncoder);
            return userRepository.save(user);
        }
    }

    public static User buildTestUser(){
        return User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(PASSWORD)
                .build();
    }
}
