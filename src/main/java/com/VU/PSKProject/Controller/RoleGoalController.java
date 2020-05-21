package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import com.VU.PSKProject.Service.Model.TeamGoalDTO;
import com.VU.PSKProject.Service.Model.WorkerDTO;
import com.VU.PSKProject.Service.RoleGoalService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import com.VU.PSKProject.Service.RoleService;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<RoleGoalDTO>> getRoleGoals(){

        List<RoleGoal> roleGoals = roleGoalService.getAllRoleGoals();
        List<RoleGoalDTO> roleGoalDTOS = new ArrayList<>();
        for (RoleGoal roleGoal : roleGoals) {
            RoleGoalDTO roleGoalDTO = new RoleGoalDTO(roleGoal.getId(), roleGoal.getRole().getId(), roleGoal.getTopic().getId());
            roleGoalDTOS.add(roleGoalDTO);
        }
        return ResponseEntity.ok(roleGoalDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoleGoalDTO> getRoleGoal(@PathVariable Long id){
        Optional<RoleGoal> roleGoal =roleGoalService.getRoleGoal(id);
        if(roleGoal.isPresent()){
            RoleGoalDTO roleGoalDTO = new RoleGoalDTO(roleGoal.get().getId(), roleGoal.get().getRole().getId(), roleGoal.get().getTopic().getId());
            return ResponseEntity.ok(roleGoalDTO);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "RoleGoal with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoleGoal(@RequestBody RoleGoalDTO roleGoalDto){
        RoleGoal roleGoal = new RoleGoal();
        PropertyUtils.customCopyProperties(roleGoalDto, roleGoal);
        roleService.getRole(roleGoalDto.getRole()).ifPresent(roleGoal::setRole);
        topicService.getTopic(roleGoalDto.getTopic()).ifPresent(roleGoal::setTopic);

        List<RoleGoal> roleGoals = roleGoalService.getAllRoleGoals();
        for (RoleGoal roleg: roleGoals) {
            if (roleGoalDto.getTopic() == roleg.getTopic().getId() && roleg.getRole().getId() == roleGoalDto.getRole()){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Message", "identical goal already exists");
                return ResponseEntity.badRequest().headers(headers).build();
            }
        }
        if (roleGoalService.checkIfRoleAndTopicExist(roleGoalDto.getRole(), roleGoalDto.getTopic())) {
            roleGoalService.createRoleGoal(roleGoal);
            return ResponseEntity.ok("ok created");
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "bad role or topic");
            return ResponseEntity.badRequest().headers(headers).build();
        }
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
