package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

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
        if (workerRepository.findById(id) != null)
            workerRepository.save(worker);
    }

    public void deleteWorker(Long id) {
        if (id != null)
            workerRepository.deleteById(id);
    }
}
