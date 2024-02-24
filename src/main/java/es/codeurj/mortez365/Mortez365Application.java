package es.codeurj.mortez365;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class Mortez365Application {

	public static void main(String[] args) {
		SpringApplication.run(Mortez365Application.class, args);
	}

}
