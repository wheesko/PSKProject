package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreshmanLoginResponseModel {
    private Long userId;
    private String email; //TODO: add email regex
    private String userAuthority;
}
