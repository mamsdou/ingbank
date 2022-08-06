package fr.cogigroup.ingbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc

public class IngbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngbankApplication.class, args);
	}

}
