package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Repository.TopicRepository;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Model.CoveredTopicsTreeNodeDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayAssigneeDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public void createTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void updateTopic(Long id, Topic topic) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (topicRepository.findById(id).isPresent()){
            topic.setId(id);
            topicRepository.save(topic);
        }
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Optional<Topic> getTopic(Long id) {
        return topicRepository.findById(id);
    }

    public List<CoveredTopicsTreeNodeDTO> getAllWorkerCoveredTopics(Long workerId)
    {
        List<LearningDay> learningDays = learningDayService.getAllLearningDaysByWorkerId(workerId);
        return learningDays.stream().map(l -> topicMapper.toTreeNodeDTO(l.getTopic())).collect(Collectors.toList());
    }
}
