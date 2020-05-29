package com.VU.PSKProject.Service.Decorator;

import com.VU.PSKProject.Service.LearningTree.LearningTreeService;
import com.VU.PSKProject.Service.Model.CoveredTopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("TimedLearningTree")
public class TimeLogger implements LearningTreeService {

    @Autowired
    @Qualifier("BasicLearningTree")
    private LearningTreeService delegate;

    @Override
    public List<CoveredTopicDTO> getAllWorkerCoveredTopics(Long workerId){
        long startTime = System.nanoTime();
        List<CoveredTopicDTO> coveredTopicDTOS = delegate.getAllWorkerCoveredTopics(workerId);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;

        System.out.println("Getting worker covered topics tree took " + duration + " ms");
        return coveredTopicDTOS;
    }
}
