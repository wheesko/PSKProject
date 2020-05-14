package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.WorkerDTO;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private WorkerGoalService workerGoalService;

    @Autowired
    private WorkerMapper workerMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<Worker>> getWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        return worker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public void createWorker(@RequestBody WorkerDTO workerDto) {
        Worker worker = workerMapper.fromDTO(workerDto);

        workerService.createWorker(worker);
    }

    @PutMapping("/update/{id}")
    public void updateWorker(@RequestBody WorkerDTO workerDto, @PathVariable Long id) {
        Worker worker = null;
        if(workerService.getWorker(id).isPresent()){
            //doing this way because the best language can't make lambdas work with non-finals
            worker = workerService.getWorker(id).get();
        }
        //return some stuff here
        if(worker == null)
            return;

        PropertyUtils.customCopyProperties(workerDto, worker);

        workerService.updateWorker(id, worker);
    }

    // cascading needs to be fixed
    @DeleteMapping("/delete/{id}")
    public void deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
    }

    @GetMapping("managedTeams/{id}")
    public ResponseEntity<Worker> getWorkerByManagedTeam(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findByManagedTeamId(id);
        return worker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("workingTeams/{id}")
    public ResponseEntity<List<Worker>> getWorkersByWorkingTeam(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findByWorkingTeamId(id));
    }
}
