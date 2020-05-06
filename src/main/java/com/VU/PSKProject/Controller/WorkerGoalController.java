package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Service.WorkerGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workerGoals")
public class  WorkerGoalController {
    @Autowired
    private WorkerGoalService workerGoalService;

    @GetMapping
    public List<WorkerGoal> getWorkerGoals(){
        return workerGoalService.getAllWorkerGoals();
    }

    @GetMapping("{id}")
    public Optional<WorkerGoal> getWorkerGoal(@PathVariable Long id){
        return workerGoalService.getWorkerGoal(id);
    }

    @PostMapping
    public void createWorkerGoal(@RequestBody WorkerGoal workerGoal){
        workerGoalService.createWorkerGoal(workerGoal);
    }

    @PutMapping("{id}")
    public void updateWorkerGoal(@RequestBody WorkerGoal workerGoal, @PathVariable Long id){
        workerGoalService.updateWorkerGoal(id, workerGoal);
    }
    @DeleteMapping("{id}")
    public void deleteWorkerGoal(@PathVariable Long id){
        workerGoalService.deleteWorkerGoal(id);
    }
}
