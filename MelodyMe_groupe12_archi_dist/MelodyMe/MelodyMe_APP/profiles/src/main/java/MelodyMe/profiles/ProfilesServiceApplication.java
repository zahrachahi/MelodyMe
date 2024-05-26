package MelodyMe.profiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class ProfilesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfilesServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> {
			for (int i = 0; i < 10000000; i++) {
			kafkaTemplate.send("musiques", "hello kafka"+i);
		}
		};
	}

}
