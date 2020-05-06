package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<Worker>> getWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        return worker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public void createWorker(@RequestBody Worker worker) {
        workerService.createWorker(worker);
    }

    @PutMapping("{id}")
    public void updateWorker(@RequestBody Worker worker, @PathVariable Long id) {
        workerService.updateWorker(id, worker);
    }

    // cascading needs to be fixed
    @DeleteMapping("{id}")
    public void deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
    }

    @GetMapping("managed-teams/{id}")
    public ResponseEntity<Worker> getWokerByManagedTeam(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findByManagedTeamId(id);
        return worker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("working-teams/{id}")
    public ResponseEntity<List<Worker>> getWorkersByWorkingTeam(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findByWorkingTeamId(id));
    }
}
