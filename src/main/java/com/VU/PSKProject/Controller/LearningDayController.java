package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.UserService;
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
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByManagerId(user);
        return learningDayMapper.mapLearningDayListToReturnDTO(learningDays);
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


    @PutMapping("/update/{id}")
    public void updateLearningEvent(@RequestBody LearningDayToCreateDTO learningDayDto, @PathVariable Long id) {
        LearningDay learningDay = learningDayService.getLearningDayById(id);
        //workerService.getWorker(learningDayDto.getAssignee()).ifPresent(learningDay::setAssignee);
        PropertyUtils.customCopyProperties(learningDayDto, learningDay);
        learningDayService.updateLearningDay(learningDay, id);
    }
	
    @DeleteMapping("/delete/{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningDayService.deleteLearningDay(id);
    }
}
