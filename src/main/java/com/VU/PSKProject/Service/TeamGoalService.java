package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Repository.TeamGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamGoalService {
    @Autowired
    private TeamGoalRepository teamGoalRepository;

    public List<TeamGoal> getAllTeamGoals() { return teamGoalRepository.findAll();
    }

    public void createTeamGoal(TeamGoal teamGoal) { teamGoalRepository.save(teamGoal);
    }

    public void updateTeamGoal(Long id, TeamGoal teamGoal) {
        if (teamGoalRepository.findById(id) != null) teamGoalRepository.save(teamGoal);
    }

    public void deleteTeamGoal(Long id) {
        teamGoalRepository.deleteById(id);
    }

    public Optional<TeamGoal> getTeamGoal(Long id) {
        return teamGoalRepository.findById(id);
    }

}
