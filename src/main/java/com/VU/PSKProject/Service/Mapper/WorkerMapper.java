package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.Worker.UserToRegisterDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.VU.PSKProject.Utils.PropertyUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

    public List<WorkerToExportDTO> toExportDTOList(List<Worker> workerList){
        return workerList.stream().map(w ->{
            WorkerToExportDTO worker = new WorkerToExportDTO();
            PropertyUtils.customCopyProperties(w, worker);
            worker.setEmail(w.getUser().getEmail());
            worker.setWorkingTeam(w.getWorkingTeam().getName());
            worker.setRole(w.getRole().getName());
            return worker;
        }).collect(Collectors.toList());
    }

    // adapter to convert one dto to another
    public List<WorkerToExportDTO> toExportList(List<WorkerToGetDTO> workerList){
        return workerList.stream().map(w ->{
            WorkerToExportDTO worker = new WorkerToExportDTO();
            PropertyUtils.customCopyProperties(w, worker);
            worker.setRole(w.getRole().getName());
            worker.setWorkingTeam(w.getWorkingTeam().getName());
            if(w.getManagedTeam() != null)
                worker.setManagedTeam(w.getManagedTeam().getName());
            else
                worker.setManagedTeam("None");
            return worker;
        }).collect(Collectors.toList());
    }
}
