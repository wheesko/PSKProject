package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Controller.Model.RefreshTokenRequest;
import com.VU.PSKProject.Controller.Model.RefreshTokenResponse;
import com.VU.PSKProject.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping(value = "/refreshToken")
    public @ResponseBody RefreshTokenResponse refreshAccessToken (
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        return authenticationService.refreshAccessToken(refreshTokenRequest);
    }
}
