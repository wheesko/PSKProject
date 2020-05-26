package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.MethodJournalRecordRepository;
import com.VU.PSKProject.Service.Model.JournalDTO;
import com.VU.PSKProject.Service.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MethodJournalRecordService {

    @Autowired
    private MethodJournalRecordRepository methodJournalRepository;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TeamService teamService;

    public void createRecord(MethodJournalRecord methodJournal) { methodJournalRepository.save(methodJournal);
    }

    public List<JournalDTO> getAllRecords(UserDTO user) {
        Worker manager = workerService.getWorkerByUserId(user.getId());
        List<JournalDTO> journalDTOS = new ArrayList<>();
        List<MethodJournalRecord> records = methodJournalRepository.findAllByTeamId(manager.getManagedTeam().getId());
        for (MethodJournalRecord record: records) {
            JournalDTO journalDTO = new JournalDTO(teamService.getTeam(record.getTeamId()).get().getName(),
                    record.getUserNameSurname(), record.getTime(), record.getMethodName(), record.getClassName());
            journalDTOS.add(journalDTO);
        }
        return  journalDTOS;
    }
}
