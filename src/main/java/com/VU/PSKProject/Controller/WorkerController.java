package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.*;
import com.VU.PSKProject.Service.MailerService.EmailServiceImpl;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.UserToRegisterDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.VU.PSKProject.Service.Model.WorkerRegisterDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<List<WorkerToGetDTO>> getWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();
        for (Worker w : workers) {
            WorkerToGetDTO workerDTO = workerMapper.toGetDTO(w);
            workerDTO.setEmail(w.getUser().getEmail());
            workerDTO.setManagerId(teamService.getTeam(workerDTO.getWorkingTeam().getId()).get().getManager().getId());
            workerDTOS.add(workerDTO);
        }
        return ResponseEntity.ok(workerDTOS);
    }

    @GetMapping("/getByTopic/{topicId}")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkersByTopic(@PathVariable Long topicId, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(workerService.extractByManager(workerService.getWorkersByTopic(topicId),
                workerService.getWorkerByUserId(user.getId())));
    }

    @GetMapping("/getByTopicIds/{topicIds}/")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkersByTopicIds(@PathVariable List<Long> topicIds, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(workerService.extractByManager(workerService.getWorkersByIds(topicIds),
                workerService.getWorkerByUserId(user.getId())));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WorkerDTO> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if (worker.isPresent()) {
            WorkerDTO workerDTO = workerMapper.toDto(worker.get());
            workerDTO.setEmail(worker.get().getUser().getEmail());
            return ResponseEntity.ok(workerDTO);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Worker with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorker(@RequestBody WorkerToCreateDTO workerDto) {
        ResponseEntity<String> response = workerService.validateWorkerData(workerDto);

        if(response.getStatusCode().isError())
            return response;

        return workerService.createFreshmanWorker(workerDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorker(@RequestBody WorkerDTO workerDto, @PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if (worker.isPresent()) {
            PropertyUtils.customCopyProperties(workerDto, worker.get());

            if (workerDto.getWorkingTeam().getId().equals(workerDto.getManagedTeam().getId()))
                return ResponseEntity.badRequest().body("managed and working ids can't be same!");

            workerService.updateWorker(id, worker.get());
            return ResponseEntity.ok("Worker updated successfully");
        } else {
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

    @PutMapping("/registerWorker")
    public ResponseEntity<UserToRegisterDTO> registerWorker(@RequestBody WorkerRegisterDTO workerRegisterDTO, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(workerService.registerWorker(user,workerRegisterDTO));
    }
}
