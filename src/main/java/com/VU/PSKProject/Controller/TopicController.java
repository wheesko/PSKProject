package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.Team.TeamTopicsDTO;
import com.VU.PSKProject.Service.Model.TopicDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerTopicsDTO;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TeamService teamService;

    @GetMapping("/getAll")
    public List<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/get/{id}")
    public Optional<Topic> getTopic(@PathVariable Long id) {
        return topicService.getTopic(id);
    }

    @PostMapping("/create")
    public void createTopic(@RequestBody TopicDTO topicDto) {
        Topic topic = new Topic();
        PropertyUtils.customCopyProperties(topicDto, topic);
        topicService.createTopic(topic);
    }

    @PutMapping("/update/{id}")
    public void updateTopic(@RequestBody TopicDTO topicDto, @PathVariable Long id){
        Topic topic = new Topic();
        PropertyUtils.customCopyProperties(topicDto, topic);
        topicService.updateTopic(id,topic);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
    }


    @GetMapping("/getTeamTopicsByManagerId/{managerId}")
    public ResponseEntity<TeamTopicsDTO> getTeamsCountByTopics(@PathVariable Long managerId){
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);
        // false time means PAST, true means FUTURE
        List<Topic> topics = topicService.getTeamTopicsAndGoals(manager.get(), false);

        TeamTopicsDTO teamTopicsDTO = new TeamTopicsDTO
                (manager.get().getManagedTeam().getId(), teamService.getTeamByManager(managerId).get().getName(), managerId);

        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsPast().contains(topic.getName()))
                teamTopicsDTO.setTopicPast(topic.getName());
        }
        topics = topicService.getTeamTopicsAndGoals(manager.get(), true);
        for (Topic topic : topics) {
            if (!teamTopicsDTO.getTopicsFuture().contains(topic.getName()))
                teamTopicsDTO.setTopicFuture(topic.getName());
        }
        return ResponseEntity.ok(teamTopicsDTO);
    }
    @GetMapping("/getWorkersTopicsByManagerId/{managerId}")
    public ResponseEntity<List<WorkerTopicsDTO>> getWorkersTopicsByManager(@PathVariable Long managerId) {
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.findByWorkingTeamId(manager.get().getManagedTeam().getId());

        List<WorkerTopicsDTO> workerTopicsDTOS = new ArrayList<>();
        for (Worker worker : workers) {
            List<Topic> topics = topicService.getWorkerTopicsAndGoals(worker.getId(), false);

            WorkerTopicsDTO workerTopicsDTO = new WorkerTopicsDTO
                    (worker.getId(), worker.getName(), worker.getSurname(), managerId);
            for (Topic topic : topics) {
                if (!workerTopicsDTO.getTopicsPast().contains(topic.getName()))
                    workerTopicsDTO.setTopicPast(topic.getName());
            }
           topics = topicService.getWorkerTopicsAndGoals(worker.getId(), true);
            for (Topic topic : topics) {
                if (!workerTopicsDTO.getTopicsFuture().contains(topic.getName()))
                    workerTopicsDTO.setTopicFuture(topic.getName());
            }

            workerTopicsDTOS.add(workerTopicsDTO);

        }
        return ResponseEntity.ok(workerTopicsDTOS);
    }
}
