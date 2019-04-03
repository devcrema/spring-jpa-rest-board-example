package devcrema.spring_jpa_rest_board_example;

import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import devcrema.spring_jpa_rest_board_example.user.UserPasswordEncoder;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@AllArgsConstructor
public class SpringJpaRestBoardExampleApplication {

	private UserRepository userRepository;
	private UserPasswordEncoder userPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaRestBoardExampleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initializeAfterStartup() {
		UserFixtureGenerator.generateTestUserFixture(userRepository, userPasswordEncoder);
	}
}
