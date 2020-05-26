package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.Worker.UserToRegisterDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WorkerMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Worker fromDTO(WorkerDTO workerDTO)
    {
        return modelMapper.map(workerDTO, Worker.class);
    }

    public Worker fromDTO(WorkerToCreateDTO workerToCreateDTO){
        return modelMapper.map(workerToCreateDTO, Worker.class);
    }

    public WorkerDTO toDto(Worker worker){
        return modelMapper.map(worker, WorkerDTO.class);
    }

    public WorkerToGetDTO toGetDTO(Worker worker){
        return modelMapper.map(worker, WorkerToGetDTO.class);
    }

    public UserToRegisterDTO toRegisterDTO(Worker worker){
        return modelMapper.map(worker, UserToRegisterDTO.class);
    }
}
