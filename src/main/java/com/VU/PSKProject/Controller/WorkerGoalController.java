package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.Model.Worker.WorkerGoalDTO;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workerGoals")
public class  WorkerGoalController {
    @Autowired
    private WorkerGoalService workerGoalService;

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
        PropertyUtils.customCopyProperties(workerGoalDto, workerGoal);
        workerGoalService.createWorkerGoal(workerGoal);
    }

    @PutMapping("/update/{id}")
    public void updateWorkerGoal(@RequestBody WorkerGoalDTO workerGoalDto, @PathVariable Long id){
        WorkerGoal workerGoal = new WorkerGoal();
        PropertyUtils.customCopyProperties(workerGoalDto, workerGoal);
        workerGoalService.updateWorkerGoal(id, workerGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWorkerGoal(@PathVariable Long id){
        workerGoalService.deleteWorkerGoal(id);
    }
}
