package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Model.LearningDayDTO;
import com.VU.PSKProject.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningDayService {
    @Autowired
    private LearningDayRepository learningDayRepository;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LearningDayMapper learningDayMapper;


    public List<LearningDay> getAllLearningDaysByWorkerId(Long workerId) {
        return learningDayRepository.findAllByAssigneeId(workerId);
    }
    public List<LearningDayDTO> getMonthLearningDaysByWorkerId(String year, String month, Long workerId){
        Timestamp dateFrom = Timestamp.valueOf(DateUtils.stringsToDate(year, month, "1").minusDays(7));
        String lastDay = DateUtils.getLastDayOfMonth(year, month);
        Timestamp dateTo = Timestamp.valueOf(DateUtils.stringsToDate(year, month, lastDay).plusDays(7));
        return learningDayRepository.findAllByDateTimeAtBetweenAndAssigneeId(dateFrom, dateTo, workerId)
                .stream()
                .map(learningDayMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<LearningDay> getAllLearningDaysByWorkerId(List<Long> workerId) {
        return learningDayRepository.findAllByAssigneeIdIn(workerId);
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
            return null; //TODO: handle it somehow
        }

        List<Worker> workers = workerService.findByWorkingTeamId(team.getId());
        List<Long> ids = new ArrayList<>();
        for (Worker w: workers) {
            ids.add(w.getId());
        }

        return learningDayRepository.findAllByAssigneeIdIn(ids);
    }
}
