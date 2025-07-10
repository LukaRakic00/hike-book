package rs.hikeandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan("rs.hikeandbook.model")
@EnableJpaRepositories("rs.hikeandbook.repository")
public class HikeBookBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikeBookBackendApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "HikeBook Backend je uspešno pokrenut!";
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
