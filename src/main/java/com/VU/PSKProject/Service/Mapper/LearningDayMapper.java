package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.Model.LearnedTopicDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LearningDayMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private TopicMapper topicMapper;

    public LearningDayDTO toDTO(LearningDay learningDay) {
        return modelMapper.map(learningDay, LearningDayDTO.class);
    }

    public LearningDayToReturnDTO toReturnDTO(LearningDay day){
        LearningDayToReturnDTO dayDto = new LearningDayToReturnDTO();
        dayDto.setId(day.getId());
        dayDto.setComment(day.getComment());
        dayDto.setName(day.getName());
        dayDto.setDateTimeAt(day.getDateTimeAt());
        dayDto.setLearned(day.isLearned());
        dayDto.setTopic(topicMapper.toReturnDto(day.getTopic()));
        dayDto.setAssignee(workerMapper.toDTO(day.getAssignee()));
        return dayDto;
    }

    public LearningDay fromDTO(LearningDayDTO learningDayDTO){
        return modelMapper.map(learningDayDTO, LearningDay.class);
    }

    public LearningDay fromDTO(LearningDayToCreateDTO learningDayDTO){
        return modelMapper.map(learningDayDTO, LearningDay.class);
    }

    public List<LearningDayToReturnDTO> mapLearningDayListToReturnDTO(List<LearningDay> learningDays) {
        List<LearningDayToReturnDTO> learningDayToReturnDTOS = new ArrayList<>();
        for(LearningDay day : learningDays){
            learningDayToReturnDTOS.add(toReturnDTO(day));
        }
        return learningDayToReturnDTOS;
    }
    public LearnedTopicDTO mapToLearnedTopicDTO(LearningDay day){
        LearnedTopicDTO topicDTO = new LearnedTopicDTO();
        topicDTO.setId(day.getTopic().getId());
        topicDTO.setName(day.getTopic().getName());
        topicDTO.setDescription(day.getTopic().getDescription());
        topicDTO.setLearned(day.isLearned());
        return topicDTO;
    }
}
