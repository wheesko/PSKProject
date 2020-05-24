package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.*;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.CsvExporters.WorkerExporter.WorkerExporter;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private WorkerExporter workerExporter;

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            WorkerToGetDTO workerDTO = workerMapper.toGetDTO(w);
            workerDTO.setEmail(w.getUser().getEmail());
            workerDTO.setManagerId(teamService.getTeam(workerDTO.getWorkingTeam().getId()).get().getManager().getId());
            workerDTOS.add(workerDTO);
        }
        return ResponseEntity.ok(workerDTOS);
    }
    @GetMapping("/getByTopicAndManager/{topicId}/{managerId}")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkersByTopic(@PathVariable Long topicId, @PathVariable Long managerId) {
        Optional<Worker> manager = workerService.getWorker(managerId);

        List<Worker> workers = workerService.getWorkersByTopic(topicId);
        return manager.map(worker -> ResponseEntity.ok(workerService.extractByManager(workers, worker)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/getByTopicIdsAndManager/{topicIds}/{managerId}")
    public ResponseEntity<List<WorkerToGetDTO>> getWorkersByTopicIds(@PathVariable List<Long> topicIds, @PathVariable Long managerId) {
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.getWorkersByIds(topicIds);

        return manager.map(worker -> ResponseEntity.ok(workerService.extractByManager(workers, worker)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/exportTopicAndManager/{topicId}/{managerId}")
    public void exportCSV(@PathVariable Long topicId, @PathVariable Long managerId, HttpServletResponse response) throws Exception{
        Optional<Worker> manager = workerService.getWorker(managerId);
        List<Worker> workers = workerService.getWorkersByTopic(topicId);
        manager.ifPresent(m ->{
            List<WorkerToExportDTO> workerToExportDTOS = workerMapper.toExportList(workerService.extractByManager(workers, m));
            try {
                workerExporter.exportToCSV(workerToExportDTOS, response);
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
                workerExporter.exportToCSV(workerToExportDTOS, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WorkerToGetDTO> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerService.getWorker(id);
        if(worker.isPresent())
        {
            WorkerToGetDTO workerDTO = workerMapper.toGetDTO(worker.get());
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
}
