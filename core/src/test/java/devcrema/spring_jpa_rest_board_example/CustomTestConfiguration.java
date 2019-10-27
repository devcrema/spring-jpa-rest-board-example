package devcrema.spring_jpa_rest_board_example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan(basePackages = "devcrema.spring_jpa_rest_board_example")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class CustomTestConfiguration {
}