package inhatc.insecure.veganday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VegandayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VegandayApplication.class, args);
	}

}
