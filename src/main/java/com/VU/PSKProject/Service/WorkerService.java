package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if(!workers.contains(w) && teamId == manager.getManagedTeam().getId() && teamId == w.getWorkingTeam().getId()){
                workers.add(w);
            }
        }
        return workers;
    }

}
