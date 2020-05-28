package com.VU.PSKProject.Service.LearningTree;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.LearningDayService;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.CoveredTopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Qualifier("BasicLearningTree")
public class LearningTreeServiceImpl implements LearningTreeService {
    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<CoveredTopicDTO> getAllWorkerCoveredTopics(Long workerId)
    {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        return learningDays.stream().map(l -> topicMapper.toTreeNodeDTO(l.getTopic())).collect(Collectors.toList());
    }
}
