package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningDayRepository extends JpaRepository<LearningDay, Long> {
    @Query("select le from learning_day le where le.assignee.id = :workerId")
    List<LearningDay> findAllLearningDaysByWorkerId(@Param("workerId") Long workerId);
}
