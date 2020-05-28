package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.*;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.UserToRegisterDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.VU.PSKProject.Service.Model.WorkerRegisterDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        List<WorkerToGetDTO> workerDTOS = workerService.retrieveAllWorkers();
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

    @GetMapping("/exportTopicAndManager/{topicId}/{managerId}")
    public void exportCSV(@PathVariable Long topicId, @PathVariable Long managerId, HttpServletResponse response) throws Exception{
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.getWorkersByTopic(topicId);
        manager.ifPresent(m ->{
            List<WorkerToExportDTO> workerToExportDTOS = workerMapper.toExportList(workerService.extractByManager(workers, m));
            try {
                workerService.exportToCSV(workerToExportDTOS, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/exportTopicsAndManager/{topicIds}/{managerId}")
    public void exportCSV(@PathVariable List<Long> topicIds, @PathVariable Long managerId, HttpServletResponse response){
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.getWorkersByIds(topicIds);
        manager.ifPresent(m ->{
            List<WorkerToExportDTO> workerToExportDTOS = workerMapper.toExportList(workerService.extractByManager(workers, m));
            try {
                workerService.exportToCSV(workerToExportDTOS, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/exportAllToCSV/")
    public void exportWorkers(HttpServletResponse response) {
        List<WorkerToExportDTO> workersToExport = workerMapper.toExportList(workerService.retrieveAllWorkers());
        try {
            workerService.exportToCSV(workersToExport, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WorkerToGetDTO> getWorker(@PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());

        if(!user.getUserRole().equals(UserAuthority.LEAD.toString()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Optional<Worker> worker = workerService.getWorker(id);
        if(worker.isPresent())
        {
            WorkerToGetDTO workerDTO = workerMapper.toGetDTO(worker.get());
            workerDTO.setEmail(worker.get().getUser().getEmail());

            //TODO: check if it's really our worker
            if(workerService.checkWorkerLeadRelationship(workerService.getWorkerByUserId(user.getId()), worker.get()))
                return ResponseEntity.ok(workerDTO);
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Worker with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorker(@RequestBody WorkerToCreateDTO workerDto, Principal principal) {
        ResponseEntity<String> response = workerService.validateWorkerData(workerDto);

        if(response.getStatusCode().isError())
            return response;

        return workerService.createFreshmanWorker(workerDto, principal);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorker(@RequestBody WorkerDTO workerDto, @PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if(worker.isPresent())
        {
            PropertyUtils.customCopyProperties(workerDto, worker.get());

            if(workerDto.getWorkingTeam().getId().equals(workerDto.getManagedTeam().getId()))
                return ResponseEntity.badRequest().body("managed and working ids can't be same!");

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
    public ResponseEntity<WorkerToGetDTO> getWorkerByManagedTeam(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findByManagedTeamId(id);
        return worker.map(value -> ResponseEntity.ok(workerMapper.toGetDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("workingTeams/{id}")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkersByWorkingTeam(@PathVariable Long id) {
        List<Worker> workers = workerService.findByWorkingTeamId(id);
        List<WorkerToGetDTO> workersToGet = new ArrayList<>();
        for (Worker w : workers) {
            workersToGet.add(workerMapper.toGetDTO(w));
        }

        return ResponseEntity.ok(workersToGet);
    }

    @PutMapping("/registerWorker")
    public ResponseEntity<UserToRegisterDTO> registerWorker(@RequestBody WorkerRegisterDTO workerRegisterDTO, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(workerService.registerWorker(user,workerRegisterDTO));
    }
}
