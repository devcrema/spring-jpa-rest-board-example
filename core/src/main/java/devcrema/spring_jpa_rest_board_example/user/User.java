package devcrema.spring_jpa_rest_board_example.user;

import devcrema.spring_jpa_rest_board_example.BaseAuditingEntity;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseAuditingEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String nickname;

    private String password;

    @Column(columnDefinition="bit(1) default 1")
    private boolean enabled;

    public void initialize(PasswordEncoder encoder){
        enabled = true;
        encodePassword(encoder);
    }

    public String resetPassword(PasswordEncoder encoder) {
        String resetPassword = RandomStringUtils.randomAlphabetic(6).toLowerCase() + RandomStringUtils.randomNumeric(6);
        this.password = encoder.encode(resetPassword);
        return resetPassword;
    }

    private void encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(this.password);
    }

    //UserDetails 구현부
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    //지금은 사용안함.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}