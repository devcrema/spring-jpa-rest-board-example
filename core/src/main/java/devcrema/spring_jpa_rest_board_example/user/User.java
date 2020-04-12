package devcrema.spring_jpa_rest_board_example.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devcrema.spring_jpa_rest_board_example.BaseAuditingEntity;
import devcrema.spring_jpa_rest_board_example.user.repository.GetUserProjection;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseAuditingEntity implements UserDetails, GetUserProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String nickname;

    @ToString.Exclude
    private String password;

    @Builder.Default
    private boolean enabled = true;

    public void initialize(String encodedPassword){
        this.enabled = true;
        this.password = encodedPassword;
    }

    //UserDetails 구현부
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @JsonIgnore
    @Transient
    public Collection<String> getRoles(){ //user만 구현하고 roles를 구현하지 않을 예정
        return Collections.singletonList("ROLE_USER");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}