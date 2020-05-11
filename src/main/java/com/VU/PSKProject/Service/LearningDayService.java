package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LearningDayService {
    @Autowired
    private LearningDayRepository learningDayRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<LearningDay> getAllLearningDaysByWorkerId(Long workerId) {
        return learningDayRepository.findAllLearningDaysByWorkerId(workerId);
    }
    public List<LearningDay> getAllLearningDaysByWorkerId(List<Long> workerId) {
        return learningDayRepository.findByWorkerIdIn(workerId);
    }

    public void createLearningDay(LearningDay learningDay) {
        learningDayRepository.save(learningDay);
    }

    public void updateLearningDay(LearningDay learningDay, Long learningDayId) {
        learningDay.setId(learningDayId);
        learningDayRepository.save(learningDay);
    }

    public void deleteLearningDay(Long id) {
        learningDayRepository.deleteById(id);
    }

    public List<LearningDay> getAllLearningDaysByManagerId(Long managerId) {

        // get all workers
        Optional<Worker> workerIds = workerRepository.findByManagedTeamId(managerId);

        // get all learning days
        return learningDayRepository.findByWorkers(workerIds);
    }
}
