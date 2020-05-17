package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class LearningDayMapper {

    @Autowired
    private ModelMapper modelMapper;

    public LearningDayDTO toDTO(LearningDay learningDay) {
        return modelMapper.map(learningDay, LearningDayDTO.class);
    }

    public LearningDayToReturnDTO toReturnDTO(LearningDay learningDay){return modelMapper.map(learningDay, LearningDayToReturnDTO.class);}

    public LearningDay fromDTO(LearningDayDTO learningDayDTO){
        return modelMapper.map(learningDayDTO, LearningDay.class);
    }

    public LearningDay fromDTO(LearningDayToCreateDTO learningDayDTO){
        return modelMapper.map(learningDayDTO, LearningDay.class);
    }

    public List<LearningDayToReturnDTO> mapLearningDayListToReturnDTO(List<LearningDay> learningDays)
    {
        List<LearningDayToReturnDTO> learningDaysToReturn = new LinkedList<>();
        for (LearningDay day: learningDays) {
            LearningDayToReturnDTO learningDayToReturnDTO = new LearningDayToReturnDTO();
            BeanUtils.copyProperties(day, learningDayToReturnDTO);
            learningDayToReturnDTO.setAssignee(day.getAssignee().getId());
            learningDaysToReturn.add(learningDayToReturnDTO);
        }
        return learningDaysToReturn;
    }
}
