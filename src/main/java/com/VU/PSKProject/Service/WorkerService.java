package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private LearningDayRepository learningDayRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerMapper workerMapper;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorker(Long id) {
        return workerRepository.findById(id);
    }

    public void createWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void updateWorker(Long id, Worker worker) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (workerRepository.findById(id).isPresent()){
            worker.setId(id);
            workerRepository.save(worker);
        }
    }

    public void deleteWorker(Long id) {
        if (id != null)
            workerRepository.deleteById(id);
    }

    public Optional<Worker> findByManagedTeamId(Long id) {
        return workerRepository.findByManagedTeamId(id);
    }

    public List<Worker> findByWorkingTeamId(Long id) {
        return workerRepository.findByWorkingTeamId(id);
    }

    public ResponseEntity<String> validateWorkerData(WorkerToCreateDTO workerDTO)
    {
        if(workerDTO.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("No email provided!");
        }
        if(workerDTO.getManagerId() == null){
            return ResponseEntity.badRequest().body("No manager provided!");
        }
        return ResponseEntity.ok().build();
    }
    public List<Worker> getWorkersByTopic(Long topicId) {
        return learningDayRepository.findAssigneesByTopicIdPast(topicId);
    }

    public List<Worker> getWorkersByIds(List<Long> ids){
        return learningDayRepository.findAssigneesByTopicIdsPast(ids);
    }
    public List<Worker> getWorkersByTopicsTeamManager(Long teamId, List<Long> ids, Worker manager, boolean time){
        List<Worker> workers = new ArrayList<>();
        List <Worker> allWorkers = null;
        if (!time)
            allWorkers = learningDayRepository.findAssigneesByTopicIdsPast(ids);
        if (time)
             allWorkers = learningDayRepository.findAssigneesByTopicIdsFuture(ids);

        for (Worker w: allWorkers) {
            if(!workers.contains(w) && teamId.equals(manager.getManagedTeam().getId()) && teamId.equals(w.getWorkingTeam().getId())){
                workers.add(w);
            }
        }
        return workers;
    }

    public List<WorkerToGetDTO> extractByManager(List<Worker> workers, Worker manager){
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();

        for (Worker w: workers) {
            if(w.getWorkingTeam().getId().equals(manager.getManagedTeam().getId())){
                WorkerToGetDTO workerDTO = workerMapper.toGetDTO(w);
                workerDTO.setEmail(w.getUser().getEmail());
                workerDTO.setManagerId(w.getWorkingTeam().getManager().getId());
                workerDTOS.add(workerDTO);
            }
        }
        return workerDTOS;
    }
}
