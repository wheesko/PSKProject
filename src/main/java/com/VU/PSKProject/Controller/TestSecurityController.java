package com.VU.PSKProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestSecurityController {

    @RequestMapping("/secured")
    public void SecureResource(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Accessing shared resource");
    }
}
