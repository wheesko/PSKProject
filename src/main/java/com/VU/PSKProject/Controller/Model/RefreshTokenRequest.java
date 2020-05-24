package com.VU.PSKProject.Controller.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;
    @NotNull
    private String accessToken;
}
