package com.VU.PSKProject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @GetMapping(value = "/authenticate")
    public void authenticate() {
        //Empty endpoint to use for authentication, if thrown error - means that login is not valid.
        //No additional checks required, just call this endpoint and it will if JWT is valid or not.
    }
}
