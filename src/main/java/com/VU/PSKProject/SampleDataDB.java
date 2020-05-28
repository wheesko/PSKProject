package com.VU.PSKProject;

import com.VU.PSKProject.Entity.*;
import com.VU.PSKProject.Repository.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SampleDataDB {
    WorkerRepository workerRepository;
    TopicRepository topicRepository;
    RoleRepository roleRepository;
    RoleGoalRepository roleGoalRepository;
    TeamGoalRepository teamGoalRepository;
    TeamRepository teamRepository;
    UserRepository userRepository;
    WorkerGoalRepository workerGoalRepository;
    LearningDayRepository learningDayRepository;
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Worker> workers = new ArrayList<>();
    ArrayList<Team> teams = new ArrayList<>();
    ArrayList<Role> roles = new ArrayList<>();
    Topic gd, ge, cpp, cs, oop, ads, c;

    public SampleDataDB(WorkerRepository workerRepository, TopicRepository topicRepository, RoleRepository roleRepository, RoleGoalRepository roleGoalRepository, TeamGoalRepository teamGoalRepository, TeamRepository teamRepository, UserRepository userRepository, WorkerGoalRepository workerGoalRepository, LearningDayRepository learningDayRepository) {
        this.workerRepository = workerRepository;
        this.topicRepository = topicRepository;
        this.roleRepository = roleRepository;
        this.roleGoalRepository = roleGoalRepository;
        this.teamGoalRepository = teamGoalRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.workerGoalRepository = workerGoalRepository;
        this.learningDayRepository = learningDayRepository;
    }

    public void deleteEverything(){
        learningDayRepository.deleteAll();
        workerRepository.deleteAll();
        workerGoalRepository.deleteAll();
        topicRepository.deleteAll();
        roleGoalRepository.deleteAll();
        roleRepository.deleteAll();
        teamGoalRepository.deleteAll();
        userRepository.deleteAll();
        teamRepository.deleteAll();
    }
    public void saveUsers(){
        users.add(new User(
                "admin",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.LEAD
        ));

        users.add(new User(
                "admin1",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.LEAD
        ));

        users.add(new User(
                "worker",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.WORKER
        ));

        users.add(new User(
                "root",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.WORKER
        ));

        users.add(new User(
                "root1",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.WORKER
        ));

        users.add(new User(
                "root2",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.WORKER
        ));

        users.add(new User(
                "root3",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.WORKER
        ));

        users.add(new User(
                "root3",
                "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", //encoded "admin" string
                UserAuthority.LEAD
        ));

        userRepository.saveAll(users);
    }
    public void saveRoles(){
        roles.add(new Role("Developer", null));
        roles.add(new Role("Brain Developer", null));
        roleRepository.saveAll(roles);
    }
    public void saveTopics(){
        ArrayList<Topic> topics = new ArrayList<Topic>();

		/*topics.add(new Topic("Why C# is better than Java", null, "change my mind", null));
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
		topics.add(new Topic("Thinking sharp using Java", topics.get(3), "description", null));*/

        ArrayList<Topic> cppChildren = new ArrayList<>();
        gd = new Topic("Game development", null, "Intro to game dev", null);
        ge = new Topic("Game Engines", null, "Intro to game engines", null);
        cppChildren.add(gd);
        cppChildren.add(ge);


        ArrayList<Topic> oopChildren = new ArrayList<>();
        cpp = new Topic("C++ Programming", cppChildren, "Intro to C++", null);
        cs = new Topic("C# Programming", null, "Intro to C#", null);
        oopChildren.add(cpp);
        oopChildren.add(cs);

        oop = new Topic("OOP Programming", oopChildren, "Intro to OOP", null);
        ads = new Topic("Algorithms & Data structures", null, "Intro to ADS", null);

        ArrayList<Topic> cChildren = new ArrayList<>();
        cChildren.add(ads);
        cChildren.add(oop);
        c = new Topic("C Programming", cChildren, "Intro to C", null);


        topics.add(gd);
        topics.add(ge);
        topics.add(cpp);
        topics.add(cs);
        topics.add(oop);
        topics.add(ads);
        topics.add(c);
        topicRepository.saveAll(topics);
    }
    public void saveTeams(){
        teams.add(new Team("Devs", null, null, null));
        teams.add(new Team("Testers", null, null, null));
        teams.add(new Team("HRs", null, null, null));
        teamRepository.saveAll(teams);
    }
    public void saveWorkers(){
        workers.add(new Worker("John", "Wick", users.get(0), teams.get(0), teams.get(0), roles.get(0),
                5, 5,
                null, null));
        workers.add(new Worker("Tony", "Stark", users.get(1), teams.get(1), teams.get(0), roles.get(0),
                5, 5,
                null, null));
        workers.add(new Worker("Tony", "Montana", users.get(7), teams.get(2), teams.get(2), roles.get(0),
                5, 5,
                null, null));
        workerRepository.saveAll(workers);
    }
    public void saveLearningDays(){
        ArrayList<LearningDay> learningDayList = new ArrayList<>();
        Timestamp time = new Timestamp(new Date().getTime());

        learningDayList.add(new LearningDay("Study C", "C Study", time, workers.get(0),c));
        learningDayList.add(new LearningDay("Study OOP", "OOP Study", new Timestamp(time.getTime() + (1000*3600*25)), workers.get(0),oop));
        learningDayList.add(new LearningDay("Study CPP", "CPP Study", new Timestamp(time.getTime() + (1000*3600*50)), workers.get(0),cpp));
        learningDayList.add(new LearningDay("Study Gamedev", "Gamedev Study", new Timestamp(time.getTime() + (1000*3600*75)), workers.get(0), gd));
        learningDayList.add(new LearningDay("Study ADS", "ADS Study",new Timestamp(time.getTime() + (1000*3600*100)) , workers.get(0),ads));
        learningDayRepository.saveAll(learningDayList);
    }

    public void saveTopicsLast(){
        ArrayList<Topic> devTopics = new ArrayList<>();

        Topic Angular = new Topic("Learn Angular", null, "Angular study", null);
        Topic React = new Topic("Learn React", null, "React study", null);
        Topic Vue = new Topic("Learn Vue", null, "Vue study", null);
        ArrayList<Topic> fe = new ArrayList<>();
        fe.add(Angular);
        fe.add(React);
        fe.add(Vue);
        Topic Fe = new Topic("Learn Frontend", fe, "Frontend study", null);

        Topic ios = new Topic("Learn ios", null, "ios study", null);
        Topic android = new Topic("Learn android", null, "android study", null);
        Topic nativePlatform = new Topic("Learn nativePlatform", null, "nativePlatform study", null);
        ArrayList<Topic> mob = new ArrayList<>();
        mob.add(ios);
        mob.add(android);
        mob.add(nativePlatform);
        Topic mobile = new Topic("Learn mobile", mob, "Frontend mobile", null);

        devTopics.add(Fe);
        devTopics.add(mobile);
        Topic MobileDev = new Topic("Learn Mobile development", devTopics, "Mobile development course", null);

        ArrayList<Topic> allTopics = new ArrayList<>();
        allTopics.add(Angular);
        allTopics.add(React);
        allTopics.add(Vue);
        allTopics.add(Fe);
        allTopics.add(ios);
        allTopics.add(android);
        allTopics.add(nativePlatform);
        allTopics.add(mobile);
        allTopics.add(MobileDev);

        topicRepository.saveAll(allTopics);
    }

    public void saveWorkersLast(){
        workerRepository.saveAll(Arrays.asList(
                new Worker("Alice", "A.", users.get(2), null, teams.get(1),  roles.get(1),
                        5, 5,
                        null, null),
                new Worker("Bob", "B.", users.get(3), null, teams.get(1),  roles.get(1),
                        5, 5,
                        null, null),
                new Worker("Jack", "D.", users.get(4), null, teams.get(1), roles.get(0),
                        5, 5,
                        null, null),
                new Worker("Jack", "Murphy", users.get(5), null, teams.get(2), roles.get(0),
                        5, 5,
                        null, null),
                new Worker("John", "Connor", users.get(6), null, teams.get(2), roles.get(0),
                        5, 5,
                        null, null)
        ));
    }
}
