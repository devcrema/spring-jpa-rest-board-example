package devcrema.spring_jpa_rest_board_example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableResourceServer
@Configuration
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/users").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/posts",
                        "/api/posts/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()
                .anyRequest().access("#oauth2.hasScope('USER')");
    }

}