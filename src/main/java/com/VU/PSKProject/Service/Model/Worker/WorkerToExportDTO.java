package com.VU.PSKProject.Service.Model.Worker;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerToExportDTO {
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String surname;
    @CsvBindByPosition(position = 2)
    private String email;
    @CsvBindByPosition(position = 3)
    private String role;
    @CsvBindByPosition(position = 4)
    private String workingTeam;
}
