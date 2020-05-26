package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MethodJournalRecordRepository extends JpaRepository<MethodJournalRecord, Long>{

    List<MethodJournalRecord> findAllByTeamId(Long teamId);
}
