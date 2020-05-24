package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.TopicRepository;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.CoveredTopicsTreeNodeDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayAssigneeDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.TopicDTO;
import com.VU.PSKProject.Service.Model.Team.TeamTopicsDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerTopicsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private LearningDayService learningDayService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TeamService teamService;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public void createTopic(Topic topic) {
        topicRepository.save(topic);
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

    public List<CoveredTopicsTreeNodeDTO> getAllWorkerCoveredTopics(Long workerId)
    {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        return learningDays.stream().map(l -> topicMapper.toTreeNodeDTO(l.getTopic())).collect(Collectors.toList());
    }
    public List<Topic> getTeamTopicsAndGoals(Worker manager, boolean time){
        List<Topic> topics = null;
        if(!time)
            topics = learningDayService.getTopicsByTeamPast(manager.getManagedTeam().getId());
        if(time)
            topics = learningDayService.getTopicsByTeamFuture(manager.getManagedTeam().getId());
        return topics;
    }
    public List<Topic> getWorkerTopicsAndGoals(Long workerId, boolean time){
        List<Topic> topics = null;
        if(!time)
            topics = learningDayService.getTopicsByWorkerPast(workerId);
        if(time)
            topics = learningDayService.getTopicsByWorkerFuture(workerId);
        return topics;
    }

    public List<WorkerTopicsDTO> getWorkersTopicsDTObyManager(Long managerId){
        List<WorkerTopicsDTO> workerTopicsDTOS = new ArrayList<>();
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.findByWorkingTeamId(manager.get().getManagedTeam().getId());
        for (Worker worker : workers) {
            List<Topic> topics = getWorkerTopicsAndGoals(worker.getId(), false);

            WorkerTopicsDTO workerTopicsDTO = new WorkerTopicsDTO
                    (worker.getId(), worker.getName(), worker.getSurname(), managerId);
            for (Topic topic : topics) {
                if (!workerTopicsDTO.getTopicsPast().contains(topic.getName()))
                    workerTopicsDTO.setTopicPast(topic.getName());
            }
            topics = getWorkerTopicsAndGoals(worker.getId(), true);
            for (Topic topic : topics) {
                if (!workerTopicsDTO.getTopicsFuture().contains(topic.getName()))
                    workerTopicsDTO.setTopicFuture(topic.getName());
            }

            workerTopicsDTOS.add(workerTopicsDTO);

        }
        return workerTopicsDTOS;
    }
    public TeamTopicsDTO getTeamTopicsDTObyManager(Long managerId){
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);
        // false time means PAST, true means FUTURE
        List<Topic> topics = getTeamTopicsAndGoals(manager.get(), false);

        TeamTopicsDTO teamTopicsDTO = new TeamTopicsDTO
                (manager.get().getManagedTeam().getId(), teamService.getTeamByManager(managerId).get().getName(), managerId);

        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsPast().contains(topic.getName()))
                teamTopicsDTO.setTopicPast(topic.getName());
        }
        topics = getTeamTopicsAndGoals(manager.get(), true);
        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsFuture().contains(topic.getName()))
                teamTopicsDTO.setTopicFuture(topic.getName());
        }
        return teamTopicsDTO;
    }
}
