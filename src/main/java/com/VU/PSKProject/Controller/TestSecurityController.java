package com.VU.PSKProject.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestSecurityController {

    @PreAuthorize("hasAuthority('WORKER')")
    @GetMapping(value = "/secure")
    public Map<String, String> Test(Principal user) {
        HashMap<String, String> map = new HashMap<>();
        map.put("IsWorkerConnected", "true");
        return map;
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @GetMapping(value = "/lead")
    public Map<String, String> TestLead(Principal user) {
        HashMap<String, String> map = new HashMap<>();
        map.put("IsLeadConnected", "true");
        return map;
    }

    @GetMapping(value = "/account")
    public String getTest(Principal user) {
        System.out.print(user);
        return "Got it";
    }
}
