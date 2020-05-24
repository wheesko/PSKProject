package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.TeamRepository;
import com.VU.PSKProject.Service.Model.Team.TeamCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WorkerService workerService;
    @Autowired
    private WorkerGoalService workerGoalService;

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public void createTeam(Team team){
        teamRepository.save(team);
    }

    public void updateTeam(Long id, Team team){
        if(teamRepository.findById(id).isPresent()) {
            team.setId(id);
            teamRepository.save(team);
        }
    }

    public void deleteTeam(Long id){
        teamRepository.deleteById(id);
    }

    public Optional<Team> getTeam(Long id){
        return teamRepository.findById(id);
    }

    public Optional<Team> getTeamByManager(Long managerId){
        return teamRepository.findByManagerId(managerId);
    }

    public List<Team> getTeamsByTopicId(Long id, Long managerId){

        Optional<Worker> manager = workerService.getWorker(managerId);

        List <Team> teams = new ArrayList<>();
        List <Worker> workers = workerService.getWorkersByTopic(id);
        for (Worker worker: workers) {
            if(manager.get().getManagedTeam() != null && manager.get().getManagedTeam().getId().equals(worker.getWorkingTeam().getId()))
            {
                if(worker.getWorkingTeam() != null){
                    if (!teams.contains(worker.getWorkingTeam())){
                        teams.add(worker.getWorkingTeam());
                    }
                }
                if (worker.getManagedTeam() != null) {
                    if (!teams.contains(worker.getManagedTeam())) {
                        teams.add(worker.getManagedTeam());
                    }
                }
            }
        }
        return teams;
    }
    public List<Team> getTeamsByTopicIds(List<Long> ids, Long managerId){

        Optional<Worker> manager = workerService.getWorker(managerId);

        List <Team> teams = new ArrayList<>();
        List <Worker> workers = workerService.getWorkersByIds(ids);
        for (Worker worker: workers) {
            if(manager.get().getManagedTeam() != null && manager.get().getManagedTeam().getId().equals(worker.getWorkingTeam().getId())){
                if(worker.getWorkingTeam() != null) {
                    if (!teams.contains(worker.getWorkingTeam())){
                        teams.add(worker.getWorkingTeam());
                    }
                }
                if (worker.getManagedTeam() != null){
                    if (!teams.contains(worker.getManagedTeam())){
                        teams.add(worker.getManagedTeam());
                    }
                }
            }

        }
        return teams;
    }
    public List<TeamCountDTO> getTeamsCountDTOByTopics( List<Long> topicIds, List<Long> teamIds, Long managerId){
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);

        List<Team> teams = getAllTeams();
        List<TeamCountDTO> teamCountDTOS = new ArrayList<>();
        for (Team team: teams) {
            if(teamIds.contains(team.getId()) && manager.get().getManagedTeam().getId().equals(team.getId())){
                TeamCountDTO teamCountDTO = new TeamCountDTO();

                teamCountDTO.setId(team.getId());
                teamCountDTO.setName(team.getName());

                // false time means PAST, true means FUTURE
                teamCountDTO.setLearnedCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager.get(), false).size());

                teamCountDTO.setPlanningCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager.get(), true).size());

                teamCountDTO.setDreamingCount(workerGoalService.getWorkersByGoalsTeamManager
                        (team.getId(), topicIds, manager.get()).size());

                teamCountDTOS.add(teamCountDTO);
            }
        }
        return teamCountDTOS;
    }
}
