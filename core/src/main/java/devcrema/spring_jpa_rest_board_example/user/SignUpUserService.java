package devcrema.spring_jpa_rest_board_example.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpUserService {

    private final UserRepository userRepository;
    @Qualifier("userPasswordEncoder")
    private final PasswordEncoder userPasswordEncoder;

    public enum SignUpResult {
        DUPLICATED_EMAIL,
        DUPLICATED_NICKNAME,
        SUCCESS
    }

    @Transactional
    public SignUpResult signUp(User requestedUser) {
        if(userRepository.existsByEmail(requestedUser.getEmail())) return SignUpResult.DUPLICATED_EMAIL;
        if(userRepository.existsByNickname(requestedUser.getNickname())) return SignUpResult.DUPLICATED_NICKNAME;
        requestedUser.initialize(userPasswordEncoder);
        User user = userRepository.save(requestedUser);
        log.info("SignUp:"+user.toString());
        return SignUpResult.SUCCESS;
    }
}
