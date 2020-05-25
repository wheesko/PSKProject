package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.TeamRepository;
import com.VU.PSKProject.Service.Model.Team.TeamCountDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Utils.EventDate;
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

    public List<Team> getTeamsByTopicId(Long id, UserDTO user){
        Worker manager = workerService.getWorkerByUserId(user.getId());
        List <Team> teams = new ArrayList<>();
        List <Worker> workers = workerService.getWorkersByTopic(id);
        for (Worker worker: workers) {
            if(manager.getManagedTeam() != null && manager.getManagedTeam().getId().equals(worker.getWorkingTeam().getId()))
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
    public List<Team> getTeamsByTopicIds(List<Long> ids, UserDTO user){
        Worker manager = workerService.getWorkerByUserId(user.getId());

        List <Team> teams = new ArrayList<>();
        List <Worker> workers = workerService.getWorkersByIds(ids);
        for (Worker worker: workers) {
            if(manager.getManagedTeam() != null && manager.getManagedTeam().getId().equals(worker.getWorkingTeam().getId())){
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
    public List<TeamCountDTO> getTeamsCountDTOByTopics( List<Long> topicIds, List<Long> teamIds, UserDTO user){

        Worker manager = workerService.getWorkerByUserId(user.getId());

        List<Team> teams = getAllTeams();
        List<TeamCountDTO> teamCountDTOS = new ArrayList<>();
        for (Team team: teams) {
            if(teamIds.contains(team.getId()) && manager.getManagedTeam().getId().equals(team.getId())){
                TeamCountDTO teamCountDTO = new TeamCountDTO();

                teamCountDTO.setId(team.getId());
                teamCountDTO.setName(team.getName());

                teamCountDTO.setLearnedCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager, EventDate.eventDate.PAST).size());

                teamCountDTO.setPlanningCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager, EventDate.eventDate.FUTURE).size());

                teamCountDTO.setDreamingCount(workerGoalService.getWorkersByGoalsTeamManager
                        (team.getId(), topicIds, manager).size());

                teamCountDTOS.add(teamCountDTO);
            }
        }
        return teamCountDTOS;
    }
}
