package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import com.VU.PSKProject.Service.MethodJournalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class MethodJournalRecordController {
    @Autowired
    MethodJournalRecordService methodJournalService;

    @GetMapping("/getAll")
    public List<MethodJournalRecord> getAllRecords() {
        return methodJournalService.getAllRecords();
    }
}
