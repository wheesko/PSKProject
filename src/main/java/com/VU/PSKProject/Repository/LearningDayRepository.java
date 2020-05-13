package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LearningDayRepository extends JpaRepository<LearningDay, Long> {
    List<LearningDay> findAllByAssigneeId(Long workerId);

    List<LearningDay> findAllByDateTimeAtBetweenAndAssigneeId(Timestamp dateFrom, Timestamp dateTo, Long workerId);
}
