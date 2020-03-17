package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningEvent;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LearningEventService {
    @Autowired
    private LearningEventRepository learningEventRepository;

    public List<LearningEvent> getAllLearningEventsByWorkerId(Long workerId) {
        return learningEventRepository.findAllLearningEventsByWorkerId(workerId);
    }

    public void createLearningEvent(LearningEvent learningEvent) {
        learningEventRepository.save(learningEvent);
    }

    public void updateLearningEvent(LearningEvent learningEvent, Long learningEventId) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (learningEventRepository.findAllById(Collections.singleton(learningEventId)) != null)
            learningEventRepository.save(learningEvent);
    }

    public void deleteLearningEvent(Long id) {
        learningEventRepository.deleteById(id);
    }
}
