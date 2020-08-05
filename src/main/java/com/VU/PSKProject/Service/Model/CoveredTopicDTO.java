package com.VU.PSKProject.Service.Model;


import java.util.List;
import java.util.stream.Stream;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredTopicDTO {
    private Long id;
    private String name;
    private CoveredTopicAttributesDTO attributes;
    @CsvIgnore
    private List<CoveredTopicDTO> children;

    public CoveredTopicDTO() {

    }

    public Stream<CoveredTopicDTO> flattened() {
        return Stream.concat(
                Stream.of(this),
                children.stream().flatMap(CoveredTopicDTO::flattened));
    }
}
