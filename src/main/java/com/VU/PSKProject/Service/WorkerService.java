package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserService userService;

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
}
