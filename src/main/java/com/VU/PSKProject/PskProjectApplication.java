package com.VU.PSKProject;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class PskProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(PskProjectApplication.class, args);
	}

	//TODO: This method is for dev testing only, will need to be removed later
	// Add more users if neccessary
	@Bean
	public ApplicationRunner initializer(UserRepository repository) {
		repository.deleteAll();

		return args -> repository.saveAll(Arrays.asList(
				new User(
						"admin",
						"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
						UserAuthority.LEAD
				),
				new User(
						"worker",
						"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
						UserAuthority.WORKER
				),
				new User(
						"admin1",
						"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
						UserAuthority.LEAD
				)
		));
	}
}
