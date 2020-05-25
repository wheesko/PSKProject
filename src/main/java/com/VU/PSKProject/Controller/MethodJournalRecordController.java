package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Service.MethodJournalRecordService;
import com.VU.PSKProject.Service.Model.JournalDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class MethodJournalRecordController {
    @Autowired
    MethodJournalRecordService methodJournalService;
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<JournalDTO> getAllRecords(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return methodJournalService.getAllRecords(user);
    }
}
