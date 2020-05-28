package com.VU.PSKProject.Service.Decorator;

import com.VU.PSKProject.Service.LearningTree.LearningTreeService;
import com.VU.PSKProject.Service.Model.CoveredTopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.decorator.Decorator;
import javax.decorator.Delegate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Qualifier("TimedLearningTree")
public class TimeLogger implements LearningTreeService {

    @Autowired
    @Qualifier("BasicLearningTree")
    private LearningTreeService delegate;

    @Override
    public List<CoveredTopicDTO> getAllWorkerCoveredTopics(Long workerId){
        return delegate.getAllWorkerCoveredTopics(workerId);
    }
}
