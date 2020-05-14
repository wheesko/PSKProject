package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Service.Model.LearningDayDTO;
import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learningDays")
public class LearningDayController {

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/get/{workerId}")
    public List<LearningDay> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        return learningDayService.getAllLearningDaysByWorkerId(workerId);
    }
    @GetMapping("/getByManagerId/{managerId}")
    public List<LearningDay> getAllLearningEventsByManagerId(@PathVariable Long managerId) {
        return learningDayService.getAllLearningDaysByManagerId(managerId);
    }

    @GetMapping("/get/{year}/{month}/{workerId}")
    public List<LearningDayDTO> getMonthLearningDaysByWorkerId(
        @PathVariable String year,
        @PathVariable String month,
        @PathVariable Long workerId
    ) {
        return learningDayService.getMonthLearningDaysByWorkerId(year, month, workerId);
    }

    @PostMapping("/create")
    public void createLearningEventForWorker(@RequestBody LearningDayDTO learningDayDto) {
        LearningDay learningDay = new LearningDay();
        BeanUtils.copyProperties(learningDayDto, learningDay);
        workerService.getWorker(learningDayDto.getAssignee().getId()).ifPresent(learningDay::setAssignee);
        learningDayService.createLearningDay(learningDay);
    }

    @PutMapping("/update/{id}")
    public void updateLearningEvent(@RequestBody LearningDayDTO learningDayDto, @PathVariable Long id) {
        LearningDay learningDay = new LearningDay();
        PropertyUtils.customCopyProperties(learningDayDto, learningDay);
        workerService.getWorker(learningDayDto.getAssignee().getId()).ifPresent(learningDay::setAssignee);
        learningDayService.updateLearningDay(learningDay, id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningDayService.deleteLearningDay(id);
    }
}
