package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.TeamRepository;
import com.VU.PSKProject.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    public List<Team> getTeamsByTopicId(Long id){
        List <Team> teams = new ArrayList<>();
        List <Worker> workers = workerService.getWorkersByTopic(id);
        for (Worker worker: workers) {
            if (!teams.contains(worker.getWorkingTeam())){
                teams.add(worker.getWorkingTeam());
            }
            // NF-7: Parodyti, kokios komandos mokesi tam tikra tema. does that include manager? if u think yes, then uncomment dis
            //if (!teams.contains(worker.getManagedTeam())){
           //     teams.add(worker.getManagedTeam());
           // }
        }
        return teams;
    }
}
