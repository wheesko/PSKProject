package com.VU.PSKProject;

import com.VU.PSKProject.Entity.*;
import com.VU.PSKProject.Repository.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class PskProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(PskProjectApplication.class, args);
	}

	//TODO: This method is for dev testing only, will need to be removed later
	// Add more users if neccessary
	@Bean
	public ApplicationRunner initializer(WorkerRepository workerRepository, TopicRepository topicRepository,
										 RoleRepository roleRepository, RoleGoalRepository roleGoalRepository,
										 TeamGoalRepository teamGoalRepository, TeamRepository teamRepository,
										 UserRepository userRepository, WorkerGoalRepository workerGoalRepository,
										 LearningDayRepository learningDayRepository) {
		//TODO: Implement initial data generation here
		workerRepository.deleteAll();
		learningDayRepository.deleteAll();
		workerGoalRepository.deleteAll();
		topicRepository.deleteAll();
		roleGoalRepository.deleteAll();
		roleRepository.deleteAll();
		teamGoalRepository.deleteAll();
		userRepository.deleteAll(); //don
		teamRepository.deleteAll(); // don


		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(
				"admin",
				"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
				UserAuthority.LEAD
		));

		users.add(new User(
				"worker",
				"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
				UserAuthority.WORKER
		));

		users.add(new User(
				"admin1",
				"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
				UserAuthority.LEAD
		));

		users.add(new User(
				"root",
				"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
				UserAuthority.LEAD
		));

		users.add(new User(
				"root1",
				"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
				UserAuthority.LEAD
		));

		ArrayList<Topic> topics = new ArrayList<Topic>();

		topics.add(new Topic("Why C# is better than Java", null, "change my mind", null));
		topics.add(new Topic("You should stop using Java", topics.get(0), "change my mind", null));
		topics.add(new Topic("Having no var is terrible", topics.get(0), "change my mind", null));
		topics.add(new Topic("Java users can't C#", topics.get(0), "change my mind", null));

		topics.add(new Topic("Software design", null, "description", null));
		topics.add(new Topic("Decorator", topics.get(4), "description", null));
		topics.add(new Topic("Strategy", topics.get(4), "description", null));
		topics.add(new Topic("Presenter", topics.get(4), "description", null));

		topics.add(new Topic("Strategy vs Template", topics.get(6), "description", null));
		topics.add(new Topic("Dependency Injection", topics.get(6), "description", null));

		topics.add(new Topic("Presenter presents", topics.get(7), "description", null));
		topics.add(new Topic("Presenter presents even more", topics.get(7), "description", null));

		topics.add(new Topic("Decorator decorates", topics.get(5), "description", null));
		topics.add(new Topic("Decorator decorates even more", topics.get(5), "description", null));

		topics.add(new Topic("Java is bad for your vision", topics.get(1), "description", null));
		topics.add(new Topic("Thinking sharp using Java", topics.get(3), "description", null));


		ArrayList<Role> roles = new ArrayList<>();
		roles.add(new Role("Developer", null));
		roles.add(new Role("Brain Developer", null));

		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("Noobs", null, null, null));
		teams.add(new Team("EvenBiggerNoobs", null, null, null));

		ArrayList<Worker> workers = new ArrayList<>();
		workers.add(new Worker("werk", "wurk", users.get(0), teams.get(0), teams.get(0), roles.get(0),
				5, 5,
				null, null));
		workers.add(new Worker("twerk", "twurk", users.get(1), teams.get(1), teams.get(1), roles.get(0),
				5, 5,
				null, null));


		return  args -> {
			userRepository.saveAll(users);
			roleRepository.saveAll(roles);
			topicRepository.saveAll(topics);
			teamRepository.saveAll(teams);
			workerRepository.saveAll(workers);
			workerRepository.saveAll(Arrays.asList(
					new Worker("qwerk", "qwurk", users.get(2), null, teams.get(0),  roles.get(1),
							5, 5,
							null, null),
					new Worker("swerk", "swurk", users.get(3), null, teams.get(0),  roles.get(1),
							5, 5,
							null, null),
					new Worker("kwerk", "kwurk", users.get(4), null, teams.get(1), roles.get(0),
							5, 5,
							null, null)
			));
		};
	}
}
