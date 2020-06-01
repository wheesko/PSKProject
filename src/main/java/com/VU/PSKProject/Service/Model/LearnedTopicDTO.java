package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearnedTopicDTO {
    private long id;
    private String name;
    private String description;
    private boolean learned;
}
