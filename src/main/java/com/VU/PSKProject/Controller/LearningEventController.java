package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.LearningEvent;
import com.VU.PSKProject.Service.LearningEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("learningEvents")
public class LearningEventController {
    @Autowired
    private LearningEventService learningEventService;

    @GetMapping("{workerId}")
    public List<LearningEvent> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        return learningEventService.getAllLearningEventsByWorkerId(workerId);
    }

    @PostMapping
    public void createLearningEventForWorker(@RequestBody LearningEvent learningEvent) {
        learningEventService.createLearningEvent(learningEvent);
    }

    @PutMapping("{id}")
    public void updateLearningEvent(@RequestBody LearningEvent learningEvent, @PathVariable Long id) {
        learningEventService.updateLearningEvent(learningEvent, id);
    }
    @DeleteMapping("{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningEventService.deleteLearningEvent(id);
    }
}
