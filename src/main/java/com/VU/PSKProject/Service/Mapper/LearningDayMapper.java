package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.Model.LearningDayDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearningDayMapper {

    @Autowired
    private ModelMapper modelMapper;

    public LearningDayDTO toDTO(LearningDay learningDay) {
        return modelMapper.map(learningDay, LearningDayDTO.class);
    }
}
