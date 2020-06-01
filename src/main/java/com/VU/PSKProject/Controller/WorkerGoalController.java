package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerGoalDTO;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workerGoals")
public class  WorkerGoalController {
    @Autowired
    private WorkerGoalService workerGoalService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @GetMapping("/getOwn")
    public ResponseEntity<List<TopicToReturnDTO>> getOwnGoals(Principal principal){
        UserDTO user = userService.getUserByEmail(principal.getName());

        return ResponseEntity.ok(workerGoalService.getWorkerGoalsByWorker(user));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkerGoalDTO>> getWorkerGoals(){
        List<WorkerGoal> workerGoals = workerGoalService.getAllWorkerGoals();
        List<WorkerGoalDTO> workerGoalDTOS = new ArrayList<>();
        for (WorkerGoal workerGoal : workerGoals) {
            WorkerGoalDTO workerGoalDTO = new WorkerGoalDTO(workerGoal.getId(), workerGoal.getWorker().getId(), workerGoal.getTopic().getId() );
            workerGoalDTOS.add(workerGoalDTO);
        }
        return ResponseEntity.ok(workerGoalDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WorkerGoalDTO> getWorkerGoal(@PathVariable Long id){
        Optional<WorkerGoal> workerGoal = workerGoalService.getWorkerGoal(id);
        if(workerGoal.isPresent()){
            WorkerGoalDTO workerGoalDTO = new WorkerGoalDTO(workerGoal.get().getId(), workerGoal.get().getWorker().getId(), workerGoal.get().getTopic().getId() );
            return ResponseEntity.ok(workerGoalDTO);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "WorkerGoal with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorkerGoal(@RequestBody WorkerGoalDTO workerGoalDto){
        WorkerGoal workerGoal = new WorkerGoal();
        BeanUtils.copyProperties(workerGoalDto, workerGoal);
        workerService.getWorker(workerGoalDto.getWorker()).ifPresent(workerGoal::setWorker);
        topicService.getTopic(workerGoalDto.getTopic()).ifPresent(workerGoal::setTopic);

        List<WorkerGoal> workerGoals = workerGoalService.getAllWorkerGoals();

        for (WorkerGoal workerg: workerGoals) {
            if (workerGoalDto.getTopic().equals(workerg.getTopic().getId()) && workerg.getWorker().getId().equals(workerGoalDto.getWorker())){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Message", "identical goal already exists");
                return ResponseEntity.badRequest().headers(headers).build();
            }
        }
        if (workerGoalService.checkIfWorkerAndTopicExist(workerGoalDto.getWorker(), workerGoalDto.getTopic())) {
            workerGoalService.createWorkerGoal(workerGoal);
            return ResponseEntity.ok("ok created");
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "bad worker or topic");
            return ResponseEntity.badRequest().headers(headers).build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWorkerGoal(@PathVariable Long id){
        workerGoalService.deleteWorkerGoal(id);
    }
}
