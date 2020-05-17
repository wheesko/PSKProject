package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import com.VU.PSKProject.Service.WorkerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/learningDays")
public class LearningDayController {

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private LearningDayMapper learningDayMapper;

    @GetMapping("/get/{workerId}")
    public List<LearningDayToReturnDTO> getAllLearningEventsByWorkerId(@PathVariable Long workerId) {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        return learningDayMapper.mapLearningDayListToReturnDTO(learningDays);
    }
    @GetMapping("/getByManagerId/{managerId}")
    public List<LearningDayToReturnDTO> getAllLearningEventsByManagerId(@PathVariable Long managerId) {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByManagerId(managerId);
        return learningDayMapper.mapLearningDayListToReturnDTO(learningDays);
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
        LearningDay learningDay = learningDayMapper.fromDTO(learningDayDto);
        workerService.getWorker(learningDayDto.getAssignee()).ifPresent(learningDay::setAssignee);
        learningDayService.createLearningDay(learningDay);
    }

    @PutMapping("/update/{id}")
    public void updateLearningEvent(@RequestBody LearningDayDTO learningDayDto, @PathVariable Long id) {
        LearningDay learningDay = learningDayMapper.fromDTO(learningDayDto);
        workerService.getWorker(learningDayDto.getAssignee()).ifPresent(learningDay::setAssignee);
        learningDayService.updateLearningDay(learningDay, id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLearningEvent(@PathVariable Long id) {
        learningDayService.deleteLearningDay(id);
    }
}
