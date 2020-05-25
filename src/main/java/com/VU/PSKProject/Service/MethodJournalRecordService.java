package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import com.VU.PSKProject.Repository.MethodJournalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MethodJournalRecordService {

    @Autowired
    MethodJournalRecordRepository methodJournalRepository;

    public void createRecord(MethodJournalRecord methodJournal) { methodJournalRepository.save(methodJournal);
    }

    public List<MethodJournalRecord> getAllRecords() { return methodJournalRepository.findAll(); }
}
