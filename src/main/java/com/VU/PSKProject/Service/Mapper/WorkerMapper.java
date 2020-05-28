package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.VU.PSKProject.Service.Model.Worker.*;
import com.VU.PSKProject.Service.RoleService;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.stream.Collectors;

@Service
public class WorkerMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TeamService teamService;


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

    public WorkerToGetDTOStripped toGetDTO(Worker worker){
        return toDTOStripped(worker);
    }

    public WorkerToGetDTO workerToGetDTO(Worker worker){
        return modelMapper.map(worker, WorkerToGetDTO.class);
    }

    public WorkerToGetDTOManagerDTO toGetDTOManagerDTO (Worker worker) {
        return modelMapper.map(worker, WorkerToGetDTOManagerDTO.class);
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
    public List<WorkerToExportDTO> toExportList(List<WorkerToGetDTOStripped> workerList){
        return workerList.stream().map(w ->{
            WorkerToExportDTO worker = new WorkerToExportDTO();
            PropertyUtils.customCopyProperties(w, worker);
            worker.setRole(roleService.getRole(w.getRole()).get().getName());
            worker.setWorkingTeam(workerService.getWorker(w.getId()).get().getWorkingTeam().getName());
            if(w.getManagedTeam() != null)
                worker.setManagedTeam(workerService.getWorker(w.getId()).get().getManagedTeam().getName());
            else
                worker.setManagedTeam("None");
            return worker;
        }).collect(Collectors.toList());
    }
    public WorkerToGetDTOStripped toDTOStripped(Worker w){
        WorkerToGetDTOStripped workerDTO = new WorkerToGetDTOStripped(w.getId(),
                w.getName(), w.getSurname(), w.getUser().getEmail(), w.getRole().getId(),
                w.getQuarterLearningDayLimit(), w.getConsecutiveLearningDayLimit());

        try {
            workerDTO.setManagerId(teamService.getTeam(w.getWorkingTeam().getId()).get().getManager().getId());
        }
        catch(Exception e){

        }
        List<Long> days = new ArrayList<>();
        List<Long> goals = new ArrayList<>();
        for (LearningDay day:w.getLearningDays()) {
            days.add(day.getId());
        }
        for (WorkerGoal goal:w.getGoals()) {
            goals.add(goal.getId());
        }
        workerDTO.setLearningDays(days);
        workerDTO.setGoals(goals);

        if(w.getManagedTeam() != null)
            workerDTO.setManagedTeam(w.getManagedTeam().getId());
        if(w.getWorkingTeam() != null)
            workerDTO.setWorkingTeam(w.getWorkingTeam().getId());
        return workerDTO;
    }
}
