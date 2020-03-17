package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningEventRepository extends JpaRepository<LearningEvent, Long> {
    @Query("select le from LearningEvent le where le.assignedWorker.id = :workerId")
    List<LearningEvent> findAllLearningEventsByWorkerId(@Param("workerId") Long workerId);
}
