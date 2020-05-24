package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicToReturnDTO {
    private Long id;
    private String name;
    private TopicToReturnDTO parentTopic;
    private String description;
}
