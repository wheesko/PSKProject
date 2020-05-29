package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Repository.TopicRepository;
import com.VU.PSKProject.Repository.WorkerGoalRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerGoalDTO;
import com.VU.PSKProject.Service.Model.WorkerGoalDTOtoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerGoalService {
    @Autowired
    private WorkerGoalRepository workerGoalRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private WorkerRepository workerService;
    @Autowired
    private TopicMapper topicMapper;

    public List<WorkerGoal> getAllWorkerGoals() { return workerGoalRepository.findAll();
    }

    public void createWorkerGoal(WorkerGoal workerGoal) { workerGoalRepository.save(workerGoal);
    }

    public List<TopicToReturnDTO> getWorkerGoalsByWorker(UserDTO user) {
        Optional<Worker> worker = workerService.findByUserId(user.getId());
        return worker.get().getGoals().stream()
                .map(goal -> topicMapper.toReturnDto(getWorkerGoal(goal.getId()).get().getTopic()))
                .collect(Collectors.toList());
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
            if(teamId.equals(manager.getManagedTeam().getId()) && teamId.equals(w.getWorkingTeam().getId())){
                workers.add(w);
            }
        }
        return workers;
    }

    public boolean checkIfWorkerAndTopicExist(long workerId, Long topicId){
        if(topicRepository.existsById(topicId) && workerRepository.existsById(workerId))
            return true;
        else return false;
    }
}
