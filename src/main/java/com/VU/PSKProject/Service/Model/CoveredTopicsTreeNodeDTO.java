package com.VU.PSKProject.Service.Model;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoveredTopicsTreeNodeDTO {
    private String name;
    private List<CoveredTopicsTreeNodeDTO> children;
}
