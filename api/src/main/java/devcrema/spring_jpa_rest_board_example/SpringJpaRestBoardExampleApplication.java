package devcrema.spring_jpa_rest_board_example;

import devcrema.spring_jpa_rest_board_example.test_fixture.UserFixtureGenerator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringJpaRestBoardExampleApplication {

	private final UserFixtureGenerator userFixtureGenerator;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaRestBoardExampleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initializeAfterStartup() {
		userFixtureGenerator.generateTestUserFixture();
	}
}
