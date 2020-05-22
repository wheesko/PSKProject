package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Repository.RoleGoalRepository;
import com.VU.PSKProject.Repository.RoleRepository;
import com.VU.PSKProject.Repository.TopicRepository;
import com.VU.PSKProject.Service.Model.RoleDTO;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleGoalService {
    @Autowired
    private RoleGoalRepository roleGoalRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TopicRepository topicRepository;

    public List<RoleGoal> getAllRoleGoals() { return roleGoalRepository.findAll();
    }

    public void createRoleGoal(RoleGoal roleGoal) { roleGoalRepository.save(roleGoal);
    }

    public void updateRoleGoal(Long id, RoleGoal roleGoal) {
        roleGoalRepository.findById(id).ifPresent(r -> roleGoalRepository.save(r));
    }

    public void deleteRoleGoal(Long id) {
        roleGoalRepository.deleteById(id);
    }

    public Optional<RoleGoal> getRoleGoal(Long id) {
        return roleGoalRepository.findById(id);
    }

    public boolean checkIfRoleAndTopicExist(long roleId, Long topicId){
        if(topicRepository.existsById(topicId) && roleRepository.existsById(roleId))
        return true;
        else return false;
    }
}
