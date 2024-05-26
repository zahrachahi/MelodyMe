package com.postgresql.utilisateurs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "utilisateurs")
public class UtilisateursApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApplication.class, args);
	}
}

