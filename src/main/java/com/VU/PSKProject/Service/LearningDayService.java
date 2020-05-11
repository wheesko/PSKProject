package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import lombok.var;
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
    private WorkerService workerService;

    @Autowired
    private TeamService teamService;

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
         //Worker workerId = workerRepository.findByManagedTeamId(managerId);

        // get all learning days
        //return learningDayRepository.findByWorkers(workerIds);

        Team team;
        if(teamService.getTeamByManager(managerId).isPresent()){
            team = teamService.getTeamByManager(managerId).get();
        }else{
            return null; //handle it somehow
        }

        var workers = workerService.findByWorkingTeamId(team.getId());
        List<Long> ids = new ArrayList<>();
        for (Worker w: workers) {
            ids.add(w.getId());
        }

        return learningDayRepository.findByWorkerIdIn(ids);
    }
}
