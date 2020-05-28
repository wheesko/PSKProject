package com.VU.PSKProject.Controller.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicCreateRequest {
    private String description;
    private Long parentTopicId;
    private String name;
}
