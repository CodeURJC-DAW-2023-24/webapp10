package es.codeurj.mortez365;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Mortez365Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Mortez365Application.class, args);
	}

}
