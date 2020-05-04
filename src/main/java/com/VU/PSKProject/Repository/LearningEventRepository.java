package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningEventRepository extends JpaRepository<LearningDay, Long> {
    @Query("select le from LearningDay le where le.assignedWorker.id = :workerId")
    List<LearningDay> findAllLearningEventsByWorkerId(@Param("workerId") Long workerId);
}
