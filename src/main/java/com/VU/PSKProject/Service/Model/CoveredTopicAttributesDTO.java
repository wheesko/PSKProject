package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredTopicAttributesDTO {

    private String teams;
    private String workers;

    public CoveredTopicAttributesDTO(String teams, String workers) {
        this.teams = teams;
        this.workers = workers;
    }
}
