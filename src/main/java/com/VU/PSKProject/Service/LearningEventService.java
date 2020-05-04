package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Repository.LearningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LearningEventService {
    @Autowired
    private LearningEventRepository learningEventRepository;

    public List<LearningDay> getAllLearningEventsByWorkerId(Long workerId) {
        return learningEventRepository.findAllLearningEventsByWorkerId(workerId);
    }

    public void createLearningEvent(LearningDay learningDay) {
        learningEventRepository.save(learningDay);
    }

    public void updateLearningEvent(LearningDay learningDay, Long learningEventId) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (learningEventRepository.findAllById(Collections.singleton(learningEventId)) != null)
            learningEventRepository.save(learningDay);
    }

    public void deleteLearningEvent(Long id) {
        learningEventRepository.deleteById(id);
    }
}
