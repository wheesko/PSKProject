package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/learningDays")
public class LearningDayController {

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private LearningDayMapper learningDayMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{workerId}")
    public List<LearningDayToReturnDTO> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        return learningDayMapper.mapLearningDayListToReturnDTO(learningDays);
    }

    @GetMapping("/getByManagerId")
    public List<LearningDayToReturnDTO> getAllLearningEventsByManagerId(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return learningDayService.getAllLearningDaysByManagerId(user);
    }

    @GetMapping("/getByManagerId/{year}/{month}")
    public List<LearningDayToReturnDTO> getAllMonthLearningEventsByManagerId(
            @PathVariable String year,
            @PathVariable String month,
            Principal principal
    ) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return learningDayService.getAllMonthLearningDaysByManagerId(user, year, month);
    }

    @GetMapping("/get/{year}/{month}")
    public List<LearningDayToReturnDTO> getMonthLearningDaysByWorkerId(
        @PathVariable String year,
        @PathVariable String month,
        Principal principal
    ) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return learningDayService.getMonthLearningDaysByWorkerId(year, month, user);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLearningEventForWorker(
            @RequestBody LearningDayToCreateDTO learningDayToCreate,
            Principal principal
    ) {
        UserDTO user = userService.getUserByEmail(principal.getName());

        learningDayService.createLearningDay(learningDayToCreate, user);
        return ResponseEntity.ok("Learning Day has been created successfully!");
    }

    @PutMapping("/setLearned/{id}")
    public ResponseEntity<String> setLearningDayAsLearned(@PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if(learningDayService.setLearningDayAsLearned(user, id)){
            return ResponseEntity.ok("Learning day successfully marked as done!");
        }
        else
            return ResponseEntity.badRequest().body("Could not mark learning day as done!");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLearningEvent(@RequestBody LearningDayToCreateDTO learningDayDto, @PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        LearningDay learningDay =  learningDayMapper.fromDTO(learningDayDto);
        //workerService.getWorker(learningDayDto.getAssignee()).ifPresent(learningDay::setAssignee);
        PropertyUtils.customCopyProperties(learningDayDto, learningDay);
        learningDayService.updateLearningDay(learningDay, id, user);
        return ResponseEntity.ok("Learning Day has been updated successfully!");
    }
	
    @DeleteMapping("/delete/{id}")
    public void deleteLearningEvent(@PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        learningDayService.deleteLearningDay(id, user);
    }
}
