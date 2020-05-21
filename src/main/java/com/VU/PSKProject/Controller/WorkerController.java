package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.*;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
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
    private TeamService teamService;

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
    @GetMapping("/getByTopicAndManager/{topicId}/{managerId}")
    public ResponseEntity<List<WorkerDTO>> getWorkersByTopic(@PathVariable Long topicId, @PathVariable Long managerId) {
        // for this to werk we need to know managerId
        Optional<Worker> manager = workerService.getWorker(managerId);

        List<Worker> workers = workerService.getWorkersByTopic(topicId);
        List<WorkerDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            if(w.getWorkingTeam().getId() == manager.get().getManagedTeam().getId()){
                WorkerDTO workerDTO = workerMapper.toDto(w);
                workerDTO.setEmail(w.getUser().getEmail());
                workerDTOS.add(workerDTO);
            }
        }
        return ResponseEntity.ok(workerDTOS);
    }
    @GetMapping("/getByTopicIdsAndManager/{topicIds}/{managerId}")
    public ResponseEntity<List<WorkerDTO>> getWorkersByTopicIds(@PathVariable List<Long> topicIds, @PathVariable Long managerId) {

        // for this to werk we need to know managerId
        Optional<Worker> manager = workerService.getWorker(managerId);

        List<Worker> workers = workerService.getWorkersByIds(topicIds);
        List<WorkerDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            if(w.getWorkingTeam().getId() == manager.get().getManagedTeam().getId()) {
                WorkerDTO workerDTO = workerMapper.toDto(w);
                workerDTO.setEmail(w.getUser().getEmail());
                workerDTOS.add(workerDTO);
            }
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
    public ResponseEntity<String> createWorker(@RequestBody WorkerToCreateDTO workerDto) {
        Worker worker = workerMapper.fromDTO(workerDto);
        ResponseEntity<String> response = workerService.validateWorkerData(workerDto);
        User u = userService.createUserFromEmail(workerDto.getEmail());
        worker.setUser(u);
        teamService.getTeamByManager(workerDto.getManagerId()).ifPresent(worker::setWorkingTeam);
        workerService.createWorker(worker);
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorker(@RequestBody WorkerDTO workerDto, @PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if(worker.isPresent())
        {
            PropertyUtils.customCopyProperties(workerDto, worker.get());
            workerService.updateWorker(id, worker.get());
            return ResponseEntity.ok("Worker updated successfully");
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Worker with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
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
