package devcrema.spring_jpa_rest_board_example.user.application;

import devcrema.spring_jpa_rest_board_example.user.domain.User;
import devcrema.spring_jpa_rest_board_example.user.domain.UserRepository;
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

    @Transactional
    public void signUp(User signUpUser) {
        if(userRepository.existsByEmail(signUpUser.getEmail())) throw new DuplicatedEmailException();
        if(userRepository.existsByNickname(signUpUser.getNickname())) throw new DuplicatedNicknameException();
        signUpUser.initialize(userPasswordEncoder.encode(signUpUser.getPassword()));
        User user = userRepository.save(signUpUser);
        log.info("SignUp:" + user.toString());
    }
}
