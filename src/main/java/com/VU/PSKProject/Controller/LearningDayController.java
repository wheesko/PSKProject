package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("learningEvents")
public class LearningDayController {

    @Autowired
    private LearningEventService learningEventService;

    @GetMapping("{workerId}")
    public List<LearningDay> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        return learningEventService.getAllLearningEventsByWorkerId(workerId);
    }

    @PostMapping
    public void createLearningEventForWorker(@RequestBody LearningDay learningDay) {
        learningEventService.createLearningEvent(learningDay);
    }

    @PutMapping("{id}")
    public void updateLearningEvent(@RequestBody LearningDay learningDay, @PathVariable Long id) {
        learningEventService.updateLearningEvent(learningDay, id);
    }
    @DeleteMapping("{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningEventService.deleteLearningEvent(id);
    }
}
