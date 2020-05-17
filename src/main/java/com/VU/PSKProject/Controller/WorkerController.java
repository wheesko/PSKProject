package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.*;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.WorkerDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private UserService userService;

    @Autowired
    private WorkerMapper workerMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkerDTO>> getWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        List<WorkerDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            WorkerDTO workerDTO = workerMapper.toDto(w);
            workerDTO.setEmail(w.getUser().getEmail());
            workerDTOS.add(workerDTO);
        }
        return ResponseEntity.ok(workerDTOS);
    }
    @GetMapping("/getByTopic/{id}")
    public ResponseEntity<List<WorkerDTO>> getWorkersByTopic(@PathVariable Long id) {
        List<Worker> workers = workerService.getWorkersByTopic(id);
        List<WorkerDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            WorkerDTO workerDTO = workerMapper.toDto(w);
            workerDTO.setEmail(w.getUser().getEmail());
            workerDTOS.add(workerDTO);
        }
        return ResponseEntity.ok(workerDTOS);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<WorkerDTO> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if(worker.isPresent())
        {
            WorkerDTO workerDTO = workerMapper.toDto(worker.get());
            workerDTO.setEmail(worker.get().getUser().getEmail());
            return ResponseEntity.ok(workerDTO);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Worker with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public void createWorker(@RequestBody WorkerDTO workerDto) {
        Worker worker = workerMapper.fromDTO(workerDto);
        User u = userService.createUserFromEmail(workerDto.getEmail());
        worker.setUser(u);
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
