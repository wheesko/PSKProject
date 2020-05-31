package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Service.Mapper.RoleMapper;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import com.VU.PSKProject.Service.RoleGoalService;
import com.VU.PSKProject.Utils.PropertyUtils;
import com.VU.PSKProject.Service.RoleService;
import com.VU.PSKProject.Service.TopicService;
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

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TopicMapper topicMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<RoleGoalDTO>> getRoleGoals(){

        List<RoleGoal> roleGoals = roleGoalService.getAllRoleGoals();
        List<RoleGoalDTO> roleGoalDTOS = new ArrayList<>();
        for (RoleGoal roleGoal : roleGoals) {
            RoleGoalDTO roleGoalDTO = new RoleGoalDTO(roleGoal.getId(), roleMapper.toDto(roleGoal.getRole()), topicMapper.toDto(roleGoal.getTopic()));
            roleGoalDTOS.add(roleGoalDTO);
        }
        return ResponseEntity.ok(roleGoalDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoleGoalDTO> getRoleGoal(@PathVariable Long id){
        Optional<RoleGoal> roleGoal =roleGoalService.getRoleGoal(id);
        if(roleGoal.isPresent()){
            RoleGoalDTO roleGoalDTO = new RoleGoalDTO(roleGoal.get().getId(), roleMapper.toDto(roleGoal.get().getRole()), topicMapper.toDto(roleGoal.get().getTopic()));
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
        roleService.getRole(roleGoalDto.getRole().getId()).ifPresent(roleGoal::setRole);
        topicService.getTopic(roleGoalDto.getTopic().getId()).ifPresent(roleGoal::setTopic);

        List<RoleGoal> roleGoals = roleGoalService.getAllRoleGoals();
        for (RoleGoal roleg: roleGoals) {
            if (roleGoalDto.getTopic().equals(roleg.getTopic().getId()) && roleg.getRole().getId() == roleGoalDto.getRole().getId()){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Message", "identical goal already exists");
                return ResponseEntity.badRequest().headers(headers).build();
            }
        }
        if (roleGoalService.checkIfRoleAndTopicExist(roleGoalDto.getRole().getId(), roleGoalDto.getTopic().getId())) {
            roleGoalService.createRoleGoal(roleGoal);
            return ResponseEntity.ok("ok created");
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "bad role or topic");
            return ResponseEntity.badRequest().headers(headers).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoleGoal(@PathVariable Long id){
        roleGoalService.deleteRoleGoal(id);
    }
}
