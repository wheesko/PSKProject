package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Repository.TeamGoalRepository;
import com.VU.PSKProject.Repository.WorkerGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (workerGoalRepository.findById(id) != null) workerGoalRepository.save(workerGoal);
    }

    public void deleteWorkerGoal(Long id) {
        workerGoalRepository.deleteById(id);
    }

    public Optional<WorkerGoal> getWorkerGoal(Long id) {
        return workerGoalRepository.findById(id);
    }
}
