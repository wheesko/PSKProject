package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Service.RoleGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roleGoals")
public class RoleGoalController {
    @Autowired
    private RoleGoalService roleGoalService;

    @GetMapping("/getAll")
    public List<RoleGoal> getRoleGoals(){
        return roleGoalService.getAllRoleGoals();
    }

    @GetMapping("/get/{id}")
    public Optional<RoleGoal> getRoleGoal(@PathVariable Long id){
        return roleGoalService.getRoleGoal(id);
    }

    @PostMapping("/create")
    public void createRoleGoal(@RequestBody RoleGoal roleGoal){
        roleGoalService.createRoleGoal(roleGoal);
    }

    @PutMapping("/update/{id}")
    public void updateRoleGoal(@RequestBody RoleGoal roleGoal, @PathVariable Long id){
        roleGoalService.updateRoleGoal(id, roleGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteRoleGoal(@PathVariable Long id){
        roleGoalService.deleteRoleGoal(id);
    }
}
