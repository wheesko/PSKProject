package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.Model.Worker.WorkerGoalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerGoalMapper {
    @Autowired
    private ModelMapper modelMapper;

    public WorkerGoal fromDTO(WorkerGoalDTO workerGoalDTO)
    {
        return modelMapper.map(workerGoalDTO, WorkerGoal.class);
    }

    public WorkerGoalDTO toDto(WorkerGoal workerGoal){
        return modelMapper.map(workerGoal, WorkerGoalDTO.class);
    }
}
