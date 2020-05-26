package com.VU.PSKProject.Service.Model.Team;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TeamCountDTO {
    @CsvIgnore
    private Long id;
    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private int learnedCount;
    @CsvBindByPosition(position = 2)
    private int planningCount;
    @CsvBindByPosition(position = 3)
    private int goalsCount;
}
