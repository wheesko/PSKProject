package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Repository.TeamGoalRepository;
import com.VU.PSKProject.Repository.TeamRepository;
import com.VU.PSKProject.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamGoalService {
    @Autowired
    private TeamGoalRepository teamGoalRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TopicRepository topicRepository;

    public List<TeamGoal> getAllTeamGoals() { return teamGoalRepository.findAll();
    }

    public void createTeamGoal(TeamGoal teamGoal) { teamGoalRepository.save(teamGoal);
    }

    public void updateTeamGoal(Long id, TeamGoal teamGoal) {
        if (teamGoalRepository.findById(id).isPresent()){
            teamGoal.setId(id);
            teamGoalRepository.save(teamGoal);
        }
    }

    public void deleteTeamGoal(Long id) {
        teamGoalRepository.deleteById(id);
    }

    public Optional<TeamGoal> getTeamGoal(Long id) {
        return teamGoalRepository.findById(id);
    }

    public boolean checkIfTeamAndTopicExist(long teamId, Long topicId){
        if(topicRepository.existsById(topicId) && teamRepository.existsById(teamId))
            return true;
        else return false;
    }
}
