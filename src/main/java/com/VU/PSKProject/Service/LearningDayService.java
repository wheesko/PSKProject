package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.*;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Service.Exception.LearningDayException;
import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Utils.DateUtils;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private TopicService topicService;

    @Autowired
    private LearningDayMapper learningDayMapper;

    public List<LearningDay> getAllLearningDaysByWorkerId(Long workerId) {
        return learningDayRepository.findAllByAssigneeId(workerId);
    }

    public List<LearningDayToReturnDTO> getMonthLearningDaysByWorkerId(String year, String month, UserDTO user) {
        Worker worker = workerService.getWorkerByUserId(user.getId());

        Timestamp dateFrom = Timestamp.valueOf(DateUtils.stringsToDate(year, month, "1").minusDays(7));
        String lastDay = DateUtils.getLastDayOfMonth(year, month);
        Timestamp dateTo = Timestamp.valueOf(DateUtils.stringsToDate(year, month, lastDay).plusDays(7));

        List<LearningDay> learningDays =
                learningDayRepository.findAllByDateTimeAtBetweenAndAssigneeId(dateFrom, dateTo, worker.getId());

        return learningDayMapper.mapLearningDayListToReturnDTO(learningDays);
    }

    public List<LearningDay> getAllLearningDaysByWorkerId(List<Long> workerId) {
        return learningDayRepository.findAllByAssigneeIdIn(workerId);
    }
    public List<LearningDay> getAllLearningDaysByWorkerIdPast(Long workerId){
        return learningDayRepository.findByWorkerPast(workerId);
    }
    public List<LearningDay> getAllLearningDaysByWorkerIdFuture(Long workerId){
        return learningDayRepository.findByWorkerFuture(workerId);
    }

    public void createLearningDay(LearningDayToCreateDTO learningDayRequest, UserDTO user) {
        Worker worker = workerService.getWorkerByUserId(user.getId());
        Topic topic = topicService.getTopic(learningDayRequest.getTopic())
                .orElseThrow(() -> new LearningDayException("Could not find topic"));

        LearningDay learningDay = learningDayMapper.fromDTO(learningDayRequest);

        learningDay.setAssignee(worker);
        learningDay.setTopic(topic);
        learningDay.setLearned(false);

        checkWorkerAvailability(worker, learningDay, false);
        learningDayRepository.save(learningDay);
    }

    public void updateLearningDay(
            LearningDayToCreateDTO learningDayToUpdate,
            Long learningDayId,
            UserDTO user
    ) {
        Worker worker = workerService.getWorkerByUserId(user.getId());
        List<LearningDay> learningDays = getAllLearningDaysByWorkerId(worker.getId());
        if (learningDays.stream().noneMatch(l -> l.getId().equals(learningDayId))) {
            throw new LearningDayException("Worker has no such learning day");
        }

        Optional<LearningDay> learningDay = getLearningDayById(learningDayId);
        if(learningDay.isPresent()) {
            SimpleDateFormat smf = new SimpleDateFormat("yyyyMMdd");

            Timestamp oldDate = learningDay.get().getDateTimeAt();

            learningDay.get().setTopic(topicService.getTopic(learningDayToUpdate.getTopic())
                    .orElseThrow(() -> new LearningDayException("Could not find topic")));
            PropertyUtils.customCopyProperties(learningDayToUpdate, learningDay.get());
            learningDay.get().setId(learningDayId);
            try {
                if(!smf.format(oldDate).equals(smf.format(learningDay.get().getDateTimeAt())))
                    checkWorkerAvailability(worker, learningDay.get(), true);
                learningDayRepository.save(learningDay.get());
            } catch (OptimisticLockException e) {
                throw new LearningDayException("This was recently modified.");
            }
        }else{
            throw new LearningDayException("Could not find such learning day");
        }

    }

    public Optional<LearningDay> getLearningDayById(Long id)
    {
        return learningDayRepository.findById(id);
    }

    public void deleteLearningDay(Long id, UserDTO user) {
        Worker worker = workerService.getWorkerByUserId(user.getId());
        List<LearningDay> learningDays = getAllLearningDaysByWorkerId(worker.getId());
        if (learningDays.stream().noneMatch(l -> l.getId().equals(id))) {
            throw new LearningDayException("Worker has no such learning day");
        }

        learningDayRepository.deleteById(id);
    }

    public List<LearningDayToReturnDTO> getAllLearningDaysByManagerId(UserDTO user) {
        Worker manager = workerService.getWorkerByUserId(user.getId());

        Optional<Team> team = teamService.getTeamByManager(manager.getId());

        if (team.isPresent()){
            List<Long> workerIds = workerService.findByWorkingTeamId(team.get().getId()).stream()
                    .map(Worker::getId)
                    .collect(Collectors.toList());

            return learningDayMapper.mapLearningDayListToReturnDTO(
                    learningDayRepository.findAllByAssigneeIdIn(workerIds)
            );
        } else {
            throw new LearningDayException("Team not found");
        }
    }

    public List<LearningDayToReturnDTO> getAllLearningDaysByManagerId(Long id, UserDTO user) {
        Worker lead = workerService.getWorkerByUserId(user.getId());
        Worker worker = workerService.getWorker(id).get();

        if (!workerService.checkWorkerLeadRelationship(lead, worker)) {
            throw new LearningDayException("Unauthorized");
        }

        Optional<Team> team = teamService.getTeamByManager(worker.getId());

        if (team.isPresent()){
            List<Long> workerIds = workerService.findByWorkingTeamId(team.get().getId()).stream()
                    .map(Worker::getId)
                    .collect(Collectors.toList());

            return learningDayMapper.mapLearningDayListToReturnDTO(
                    learningDayRepository.findAllByAssigneeIdIn(workerIds)
            );
        } else {
            throw new LearningDayException("Team not found");
        }
    }

    public List<LearningDayToReturnDTO> getAllMonthLearningDaysByManagerId(UserDTO user, String year, String month) {
        Worker manager = workerService.getWorkerByUserId(user.getId());

        Optional<Team> team = teamService.getTeamByManager(manager.getId());

        Timestamp dateFrom = Timestamp.valueOf(DateUtils.stringsToDate(year, month, "1").minusDays(7));
        String lastDay = DateUtils.getLastDayOfMonth(year, month);
        Timestamp dateTo = Timestamp.valueOf(DateUtils.stringsToDate(year, month, lastDay).plusDays(7));

        if (team.isPresent()){
            List<Long> workerIds = workerService.findByWorkingTeamId(team.get().getId()).stream()
                    .map(Worker::getId)
                    .collect(Collectors.toList());

            return learningDayMapper.mapLearningDayListToReturnDTO(
                    learningDayRepository.findAllByDateTimeAtBetweenAndAssigneeIdIn(dateFrom, dateTo, workerIds)
            );
        } else {
            throw new LearningDayException("Team not found");
        }
    }

    private void checkWorkerAvailability(Worker worker, LearningDay learningDay, boolean sameDay) {
        List<LearningDay> learningDays = getAllLearningDaysByWorkerId(worker.getId());
        Collections.sort(learningDays);
        List<LocalDateTime> localDates = new ArrayList<>();
        for(LearningDay day: learningDays)
        {
            localDates.add(day.getDateTimeAt().toLocalDateTime());
        }
        int cons = countConsecutiveDays(localDates, learningDay.getDateTimeAt().toLocalDateTime(), sameDay);

        if(cons == -1)
            throw new LearningDayException("This day is already taken");

        if(cons > worker.getConsecutiveLearningDayLimit())
            throw new LearningDayException("Too many consequent learning days!");

        int quart = countQuarterDays(localDates, learningDay.getDateTimeAt().toLocalDateTime());

        if(quart > worker.getQuarterLearningDayLimit())
             throw new LearningDayException("Too many learning days in a quarter!");
    }

    private int countConsecutiveDays(List<LocalDateTime> dateList, LocalDateTime today, boolean sameDay)
    {
        if(!sameDay)
            dateList.add(today);

        Collections.sort(dateList);

        int count = 0;
        LocalDateTime prev = dateList.get(0);
        for (int i = 1; i < dateList.size(); i++) {
            LocalDateTime next = dateList.get(i);
            if(prev.equals(next))
                return -1;
            if (prev.plusDays(1).equals(next)) {
                count++;
            }
            else
            {
                count = 0;
            }
            prev = next;
        }

        return count+1;
    }

    private int countQuarterDays(List<LocalDateTime> dateTimeList, LocalDateTime today)
    {
        dateTimeList.add(today);

        LocalDateTime firstDayOfQuarter = today.with(today.getMonth().firstMonthOfQuarter()).
                with(TemporalAdjusters.firstDayOfMonth());

        LocalDateTime lastDayOfQuarter = firstDayOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());

        List<LocalDateTime> quarterDays = new ArrayList<>();

        for(LocalDateTime day : dateTimeList)
        {
            if(firstDayOfQuarter.compareTo(day) * day.compareTo(lastDayOfQuarter) >= 0)
                quarterDays.add(day);
        }
        return quarterDays.size()-1;
    }
    public List<Topic> getTopicsByTeamPast(Long id){
        return learningDayRepository.findTopicsByTeamPAST(id);
    }
    public List<Topic> getTopicsByTeamFuture(Long id){
        return learningDayRepository.findTopicsByTeamFuture(id);
    }
    public List<Topic> getTopicsByWorkerPast(Long id){
        return learningDayRepository.findTopicsByWorkerPAST(id);
    }
    public List<Topic> getTopicsByWorkerFuture(Long id){
        return learningDayRepository.findTopicsByWorkerFuture(id);
    }
    public List<Worker> getAssigneesByTopicIdPast(Long topicId){
        return learningDayRepository.findAssigneesByTopicIdPast(topicId);
    }
    public List<Worker> getAssigneesByTopicIdsPast(List<Long> topicIds){
        return learningDayRepository.findAssigneesByTopicIdsPast(topicIds);
    }
    public List<Worker> getAssigneesByTopicIdsFuture(List<Long> topicIds){
        return learningDayRepository.findAssigneesByTopicIdsFuture(topicIds);
    }

    public boolean setLearningDayAsLearned(UserDTO user, Long id) {
        Optional<LearningDay> learningDay = getLearningDayById(id);
        if(!learningDay.isPresent())
            return false;
        if(!learningDay.get().getAssignee().equals(workerService.getWorkerByUserId(user.getId())))
            return false;
        else{
            learningDay.get().setLearned(true);
            learningDayRepository.save(learningDay.get());
            return true;
        }
    }
}
