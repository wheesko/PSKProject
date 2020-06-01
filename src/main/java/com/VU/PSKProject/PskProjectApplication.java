package com.VU.PSKProject;

import com.VU.PSKProject.Repository.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

		SampleDataDB sampleDataDB = new SampleDataDB(workerRepository, topicRepository, roleRepository, roleGoalRepository, teamGoalRepository, teamRepository, userRepository, workerGoalRepository, learningDayRepository);
		sampleDataDB.deleteEverything();
		sampleDataDB.saveUsers();
		sampleDataDB.saveRoles();
		sampleDataDB.saveTopics();
		sampleDataDB.saveTeams();
		sampleDataDB.saveWorkers();
		sampleDataDB.saveTopicsLast();
		sampleDataDB.saveLearningDays();
		sampleDataDB.saveDoneLearningDays();
		sampleDataDB.saveRoleGoals();
		sampleDataDB.saveWorkerGoals();
		sampleDataDB.saveTeamGoals();
		return  args -> { };
	}
}