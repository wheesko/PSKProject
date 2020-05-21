package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Repository.WorkerGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerGoalService {
    @Autowired
    private WorkerGoalRepository workerGoalRepository;

    public List<WorkerGoal> getAllWorkerGoals() { return workerGoalRepository.findAll();
    }

    public void createWorkerGoal(WorkerGoal workerGoal) { workerGoalRepository.save(workerGoal);
    }

    public void updateWorkerGoal(Long id, WorkerGoal workerGoal) {
        if (workerGoalRepository.findById(id).isPresent()){
            workerGoal.setId(id);
            workerGoalRepository.save(workerGoal);
        }
    }

    public void deleteWorkerGoal(Long id) {
        workerGoalRepository.deleteById(id);
    }

    public Optional<WorkerGoal> getWorkerGoal(Long id) {
        return workerGoalRepository.findById(id);
    }

    public List<WorkerGoal> findWorkerGoalsById(Long id) {
        return workerGoalRepository.findAllById(Collections.singleton(id));
    }

    public List<WorkerGoal> findWorkerGoalsById(List<Long> ids)
    {
        return workerGoalRepository.findByWorkerIdIn(ids);
    }

    public List<Worker> getWorkersByGoalsTeamManager(Long teamId, List<Long> ids, Worker manager){
        List<Worker> workers = new ArrayList<>();
        List <Worker> allWorkers = workerGoalRepository.findWorkersByTopicIds(ids);

        for (Worker w: allWorkers) {
            if(teamId == manager.getManagedTeam().getId() && teamId == w.getWorkingTeam().getId()){
                workers.add(w);
            }
        }
        return workers;
    }
}
