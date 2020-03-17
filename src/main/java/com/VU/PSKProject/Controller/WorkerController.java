package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public List<Worker> getWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping("{id}")
    public Optional<Worker> getWorker(@PathVariable Long id) {
        return workerService.getWorker(id);
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
}
