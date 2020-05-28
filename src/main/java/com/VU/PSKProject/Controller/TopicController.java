package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Controller.Model.TopicCreateRequest;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Service.Model.CoveredTopicDTO;
import com.VU.PSKProject.Service.Model.Team.TeamTopicsDTO;
import com.VU.PSKProject.Service.Model.TopicDTO;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerTopicsDTO;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<TopicToReturnDTO> getTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/get/{id}")
    public Optional<Topic> getTopic(@PathVariable Long id) {
        return topicService.getTopic(id);
    }

    @GetMapping("/getCovered/{workerId}")
    public List<CoveredTopicDTO> getTopicsCoveredByWorker(@PathVariable Long workerId) {
        return topicService.getAllWorkerCoveredTopics(workerId);
    }

    @GetMapping("/getTopicTree")
    public List<CoveredTopicDTO> getTopicTree() {
        return topicService.getFullTree();
    }

    @PostMapping("/create")
    @Transactional
    public void createTopic(@RequestBody TopicCreateRequest topicToCreate) {
        topicService.createTopic(topicToCreate);
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

    @GetMapping("/getTeamTopics")
    public ResponseEntity<TeamTopicsDTO> getTeamsCountByTopics(Principal principal){
        UserDTO user = userService.getUserByEmail(principal.getName());
        if(!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(topicService.getTeamTopicsDTObyManager(user));
    }
    @GetMapping("/getWorkersTopics")
    public ResponseEntity<List<WorkerTopicsDTO>> getWorkersTopicsByManager(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if(!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(topicService.getWorkersTopicsDTObyManager(user));
    }

    @GetMapping("exportWorkersTopicsByManagerId")
    public void exportCSV(Principal principal) {
       List<WorkerTopicsDTO> workerTopicsDTOS = topicService.getWorkersTopicsDTObyManager(userService.getUserByEmail(principal.getName()));
    }
}
