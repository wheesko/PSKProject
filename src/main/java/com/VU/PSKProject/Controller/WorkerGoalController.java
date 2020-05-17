package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.Model.WorkerGoalDTO;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAll")
    public List<WorkerGoal> getWorkerGoals(){
        return workerGoalService.getAllWorkerGoals();
    }

    @GetMapping("/get/{id}")
    public Optional<WorkerGoal> getWorkerGoal(@PathVariable Long id){
        return workerGoalService.getWorkerGoal(id);
    }

    @PostMapping("/create")
    public void createWorkerGoal(@RequestBody WorkerGoalDTO workerGoalDto){
        WorkerGoal workerGoal = new WorkerGoal();
        BeanUtils.copyProperties(workerGoalDto, workerGoal);
        workerService.getWorker(workerGoalDto.getWorker()).ifPresent(workerGoal::setWorker);
        topicService.getTopic(workerGoalDto.getTopic()).ifPresent(workerGoal::setTopic);
        workerGoalService.createWorkerGoal(workerGoal);
    }

    @PutMapping("/update/{id}")
    public void updateWorkerGoal(@RequestBody WorkerGoalDTO workerGoalDto, @PathVariable Long id){
        WorkerGoal workerGoal = new WorkerGoal();
        BeanUtils.copyProperties(workerGoalDto, workerGoal);
        workerGoalService.updateWorkerGoal(id, workerGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWorkerGoal(@PathVariable Long id){
        workerGoalService.deleteWorkerGoal(id);
    }
}
