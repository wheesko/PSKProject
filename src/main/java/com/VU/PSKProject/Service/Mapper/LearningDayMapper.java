package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToCreateDTO;
import com.VU.PSKProject.Service.Model.LearningDay.LearningDayToReturnDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
        return learningDays.stream().map(day ->{
            LearningDayToReturnDTO learningDayToReturnDTO = new LearningDayToReturnDTO();
            PropertyUtils.customCopyProperties(day, learningDayToReturnDTO);
            learningDayToReturnDTO.setAssignee(day.getAssignee().getId());
            return learningDayToReturnDTO;
        }).collect(Collectors.toList());
    }
}
