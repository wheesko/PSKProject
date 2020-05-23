package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.LearningDayRepository;
import com.VU.PSKProject.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private LearningDayRepository learningDayRepository;

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

    public List<Topic> getTeamTopicsAndGoals(Worker manager, boolean time){
        List<Topic> topics = null;
        if(!time)
            topics = learningDayRepository.findTopicsByTeamPAST(manager.getManagedTeam().getId());
        if(time)
            topics = learningDayRepository.findTopicsByTeamFuture(manager.getManagedTeam().getId());
        return topics;
    }
    public List<Topic> getWorkerTopicsAndGoals(Long workerId, boolean time){
        List<Topic> topics = null;
        if(!time)
            topics = learningDayRepository.findTopicsByWorkerPAST(workerId);
        if(time)
            topics = learningDayRepository.findTopicsByWorkerFuture(workerId);
        return topics;
    }
}
