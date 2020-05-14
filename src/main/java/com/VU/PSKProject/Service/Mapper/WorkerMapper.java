package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.WorkerDTO;
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
}
