package com.VU.PSKProject.Service;

import com.VU.PSKProject.Controller.Model.TopicCreateRequest;
import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.TopicRepository;
import com.VU.PSKProject.Service.Exception.TopicServiceException;
import com.VU.PSKProject.Service.LearningTree.LearningTreeService;
import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.*;
import com.VU.PSKProject.Service.Model.Team.TeamTopicsDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerTopicsDTO;
import com.VU.PSKProject.Utils.EventDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService{
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private WorkerGoalService workerGoalService;

    @Autowired
    private TeamGoalService teamGoalService;

    @Autowired
    @Qualifier("TimedLearningTree")
    private LearningTreeService learningTreeService;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private LearningDayMapper learningDayMapper;

    public List<TopicToReturnDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topicMapper::toReturnDto)
                .collect(Collectors.toList());
    }

    private List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public void createTopic(TopicCreateRequest topicCreateRequest) {
        Topic topic = new Topic();
        topic.setDescription(topicCreateRequest.getDescription());
        topic.setName(topicCreateRequest.getName());

        if (topicCreateRequest.getParentTopicId() != null) {
           Topic parentTopic = getTopic(topicCreateRequest.getParentTopicId()).get();

           List<Topic> childTopics = parentTopic.getChildrenTopics();
           childTopics.add(topic);
           parentTopic.setChildrenTopics(childTopics);
           topicRepository.save(topic);
           topicRepository.save(parentTopic);
        } else {
            Topic parentTopic = getAll().stream().filter(t -> t.getName().equals("Devbridge development"))
                    .findFirst().get();

            List<Topic> childTopics = parentTopic.getChildrenTopics();
            childTopics.add(topic);
            parentTopic.setChildrenTopics(childTopics);
            topicRepository.save(topic);
            topicRepository.save(parentTopic);
            topicRepository.save(topic);
        }
    }

    public void updateTopic(Long id, Topic topic) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (topicRepository.findById(id).isPresent()){
            topic.setId(id);
            topicRepository.save(topic);
        }
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Optional<Topic> getTopic(Long id) {
        return topicRepository.findById(id);
    }

    public List<LearnedTopicDTO> getAllWorkerCoveredTopics(Long workerId) {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        List<LearnedTopicDTO> learnedTopicDTOS = new ArrayList<>();
        List<String> tempTopics = new ArrayList<>();
        for (LearningDay day: learningDays) {
            if(!tempTopics.contains(day.getTopic().getName())){
                tempTopics.add(day.getTopic().getName());
                learnedTopicDTOS.add(learningDayMapper.mapToLearnedTopicDTO(day));
            }
        }
        return learnedTopicDTOS;
    }

    public List<CoveredTopicDTO> getFullTree() {
        return getAll().stream().filter(topic -> topic.getName().equals("Devbridge development"))
                .map(topic -> {
                    CoveredTopicDTO coveredTopicDTO = topicMapper.toTreeNodeDTO(topic);
                    coveredTopicDTO.flattened().forEach(t -> {
                        String teams = teamGoalService.findTeamGoalsByTopicId(t.getId())
                                .stream()
                                .map(e -> e.getTopic().getName())
                                .collect(Collectors.joining(", "));
                        String workers = workerGoalService.findWokerGoalsByTopicId(t.getId())
                                .stream()
                                .map(e -> e.getName() + " " + e.getSurname())
                                .collect(Collectors.joining(", "));
                        t.setAttributes(new CoveredTopicAttributesDTO(teams, workers));
                    });
                    return coveredTopicDTO;
                }).collect(Collectors.toList());
    }

    public List<Topic> getTeamTopicsAndGoals(Worker manager, EventDate.eventDate time){
        List<Topic> topics = null;
        if(time.equals(EventDate.eventDate.PAST))
            topics = learningDayService.getTopicsByTeamPast(manager.getManagedTeam().getId());
        if(time.equals(EventDate.eventDate.FUTURE))
            topics = learningDayService.getTopicsByTeamFuture(manager.getManagedTeam().getId());
        return topics;
    }

    public List<Topic> getWorkerTopicsAndGoals(Long workerId, EventDate.eventDate time){
        List<Topic> topics = null;
        if(time.equals(EventDate.eventDate.PAST))
            topics = learningDayService.getTopicsByWorkerPast(workerId);
        if(time.equals(EventDate.eventDate.FUTURE))
            topics = learningDayService.getTopicsByWorkerFuture(workerId);
        return topics;
    }

    public List<WorkerTopicsDTO> getWorkersTopicsDTObyManager(UserDTO user) {
        Worker manager = workerService.getWorkerByUserId(user.getId());

        List<WorkerTopicsDTO> workerTopicsDTOS = new ArrayList<>();

        List<Worker> workers = workerService.findByWorkingTeamId(manager.getManagedTeam().getId());
        for (Worker worker : workers) {
            List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerIdPast(worker.getId());

            WorkerTopicsDTO workerTopicsDTO = new WorkerTopicsDTO
                    (worker.getId(), worker.getName(), worker.getSurname(), manager.getId());

            List<LearnedTopicDTO> learnedTopicDTOS = new ArrayList<>();
            List<String> tempTopics = new ArrayList<>();
            for (LearningDay day: learningDays) {
                if(!tempTopics.contains(day.getTopic().getName())){
                    tempTopics.add(day.getTopic().getName());
                    try{
                        learnedTopicDTOS.add(learningDayMapper.mapToLearnedTopicDTO(day));
                    }
                    catch(Exception e){}
                }
            }
            workerTopicsDTO.setTopicsPast(learnedTopicDTOS);

            learnedTopicDTOS = new ArrayList<>();
            tempTopics = new ArrayList<>();
            learningDays = learningDayService.getAllLearningDaysByWorkerIdFuture(worker.getId());
            for (LearningDay day: learningDays) {
                if(!tempTopics.contains(day.getTopic().getName())){
                    tempTopics.add(day.getTopic().getName());
                    try{
                        learnedTopicDTOS.add(learningDayMapper.mapToLearnedTopicDTO(day));
                    }
                    catch (Exception e){}
                }
            }
            workerTopicsDTO.setTopicsFuture(learnedTopicDTOS);

            workerTopicsDTOS.add(workerTopicsDTO);
        }
        return workerTopicsDTOS;
    }
    public TeamTopicsDTO getTeamTopicsDTObyManager(UserDTO user){
        Worker manager = workerService.getWorkerByUserId(user.getId());

        List<Topic> topics = getTeamTopicsAndGoals(manager, EventDate.eventDate.PAST);

        TeamTopicsDTO teamTopicsDTO = new TeamTopicsDTO
                (manager.getManagedTeam().getId(), teamService.getTeamByManager(manager.getId()).get().getName(), manager.getId());

        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsPast().contains(topic.getName()))
                teamTopicsDTO.setTopicPast(topic.getName());
        }
        topics = getTeamTopicsAndGoals(manager, EventDate.eventDate.FUTURE);
        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsFuture().contains(topic.getName()))
                teamTopicsDTO.setTopicFuture(topic.getName());
        }
        return teamTopicsDTO;
    }

    public void exportToCSV(List<WorkerToExportDTO> dataToExport, HttpServletResponse response) throws Exception {
        //TODO: implement this. but how?
        /*String[] headers = {"Name,", "Surname,", "Email,", "Role,", "Team,", "Managed team\n"};
        CSVExporter.buildExportToCSVResponse(dataToExport, headers, response);*/
    }

}
