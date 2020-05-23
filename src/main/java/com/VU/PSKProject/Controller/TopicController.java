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
        return ResponseEntity.ok(topicService.getTeamTopicsDTObyManager(managerId));
    }
    @GetMapping("/getWorkersTopicsByManagerId/{managerId}")
    public ResponseEntity<List<WorkerTopicsDTO>> getWorkersTopicsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(topicService.getWorkersTopicsDTObyManager(managerId));
    }
}
