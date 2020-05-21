package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WorkerService workerService;

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
}
