package devcrema.spring_jpa_rest_board_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringJpaRestBoardExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaRestBoardExampleApplication.class, args);
	}

}
