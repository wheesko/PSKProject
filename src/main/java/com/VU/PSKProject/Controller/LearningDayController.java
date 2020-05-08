package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learningDays")
public class LearningDayController {

    @Autowired
    private LearningDayService learningDayService;

    @GetMapping("/get/{workerId}")
    public List<LearningDay> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        return learningDayService.getAllLearningDaysByWorkerId(workerId);
    }

    @PostMapping("/create")
    public void createLearningEventForWorker(@RequestBody LearningDay learningDay) {
        learningDayService.createLearningDay(learningDay);
    }

    @PutMapping("/update/{id}")
    public void updateLearningEvent(@RequestBody LearningDay learningDay, @PathVariable Long id) {
        learningDayService.updateLearningDay(learningDay, id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningDayService.deleteLearningDay(id);
    }
}
