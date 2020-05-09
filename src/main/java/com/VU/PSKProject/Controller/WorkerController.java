package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.WorkerDTO;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

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
        Worker worker = new Worker();
        BeanUtils.copyProperties(workerDto, worker);
        workerService.createWorker(worker);
    }

    @PutMapping("/update/{id}")
    public void updateWorker(@RequestBody WorkerDTO workerDto, @PathVariable Long id) {
        Worker worker = new Worker();
        BeanUtils.copyProperties(workerDto, worker);
        workerService.updateWorker(id, worker);
    }

    // cascading needs to be fixed
    @DeleteMapping("/delete/{id}")
    public void deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
    }

    @GetMapping("managedTeams/{id}")
    public ResponseEntity<Worker> getWokerByManagedTeam(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findByManagedTeamId(id);
        return worker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("workingTeams/{id}")
    public ResponseEntity<List<Worker>> getWorkersByWorkingTeam(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findByWorkingTeamId(id));
    }
}
