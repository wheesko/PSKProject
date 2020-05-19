package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import com.VU.PSKProject.Service.RoleGoalService;
import com.VU.PSKProject.Service.RoleService;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roleGoals")
public class RoleGoalController {
    @Autowired
    private RoleGoalService roleGoalService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/getAll")
    public List<RoleGoal> getRoleGoals(){
        return roleGoalService.getAllRoleGoals();
    }

    @GetMapping("/get/{id}")
    public Optional<RoleGoal> getRoleGoal(@PathVariable Long id){
        return roleGoalService.getRoleGoal(id);
    }

    @PostMapping("/create")
    public void createRoleGoal(@RequestBody RoleGoalDTO roleGoalDto){
        RoleGoal roleGoal = new RoleGoal();
        PropertyUtils.customCopyProperties(roleGoalDto, roleGoal);
        roleService.getRole(roleGoalDto.getRole()).ifPresent(roleGoal::setRole);
        topicService.getTopic(roleGoalDto.getTopic()).ifPresent(roleGoal::setTopic);
        roleGoalService.createRoleGoal(roleGoal);
    }

    @PutMapping("/update/{id}")
    public void updateRoleGoal(@RequestBody RoleGoalDTO roleGoalDto, @PathVariable Long id){
        RoleGoal roleGoal = new RoleGoal();
        PropertyUtils.customCopyProperties(roleGoalDto, roleGoal);
        roleGoalService.updateRoleGoal(id, roleGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteRoleGoal(@PathVariable Long id){
        roleGoalService.deleteRoleGoal(id);
    }
}
