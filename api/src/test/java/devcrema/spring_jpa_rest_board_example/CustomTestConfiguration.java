package devcrema.spring_jpa_rest_board_example;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan(basePackages = "devcrema.spring_jpa_rest_board_example")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ActiveProfiles(profiles = "test")
public class CustomTestConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}