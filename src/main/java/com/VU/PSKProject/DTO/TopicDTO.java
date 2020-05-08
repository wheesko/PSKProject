package com.VU.PSKProject.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicDTO {
    private Long id;
    private String name;
    private Long parentTopicId;
    private String description;
    private List<Long> teamGoals;
}
