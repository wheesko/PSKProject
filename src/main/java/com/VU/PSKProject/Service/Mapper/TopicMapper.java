package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Service.Model.CoveredTopicsTreeNodeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CoveredTopicsTreeNodeDTO toTreeNodeDTO(Topic topic){
        return modelMapper.map(topic, CoveredTopicsTreeNodeDTO.class);
    }
}
