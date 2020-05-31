package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Service.Model.LearnedTopicDTO;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkerTopicsDTO {

    @CsvIgnore
    private Long id;
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String surname;

    @CsvIgnore
    private Long manager;

    @CsvBindByPosition(position = 2)
    private List<LearnedTopicDTO> topicsPast = new ArrayList<>();
    @CsvBindByPosition(position = 3)
    private List<LearnedTopicDTO> topicsFuture = new ArrayList<>();

    public WorkerTopicsDTO(Long id, String name, String surname, Long manager){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.manager = manager;
    }
    public WorkerTopicsDTO(){}

}
