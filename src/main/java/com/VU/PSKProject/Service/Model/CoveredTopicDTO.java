package com.VU.PSKProject.Service.Model;


import java.util.List;

import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredTopicDTO {
    private String name;
    @CsvIgnore
    private List<CoveredTopicDTO> children;
}
