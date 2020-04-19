package devcrema.spring_jpa_rest_board_example.user.infrastructure;

import devcrema.spring_jpa_rest_board_example.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("user not found :[" + id + "]"));
    }

}
