package banasiewicz.pawel.Unichess.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnichessBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnichessBackendApplication.class, args);
	}
}
