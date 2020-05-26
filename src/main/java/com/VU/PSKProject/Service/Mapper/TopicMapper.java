package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Service.Model.CoveredTopicDTO;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TopicToReturnDTO toReturnDto(Topic topic) {
        return modelMapper.map(topic, TopicToReturnDTO.class);
    }

    public CoveredTopicDTO toTreeNodeDTO(Topic topic){
        return modelMapper.map(topic, CoveredTopicDTO.class);
    }
}
