package devcrema.spring_jpa_rest_board_example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     *
     * @param id 유저 id
     * @return UserDetails 구현체
     * @throws UsernameNotFoundException 해당 email 을 가진 유저가 없을 경우
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException(id + " 해당 유저를 찾을 수 없습니다."));
    }

}
