package com.VU.PSKProject.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestSecurityController {
    @GetMapping(value = "/secure")
    public Map<String, String> Test() {
        System.out.println("Test");
        HashMap<String, String> map = new HashMap<>();
        map.put("IsWorkerConnected", "true");
        return map;
    }

    @GetMapping(value = "/lead")
    public Map<String, String> TestLead() {
        System.out.println("TestLead");
        HashMap<String, String> map = new HashMap<>();
        map.put("IsLeadConnected", "true");
        return map;
    }
}
