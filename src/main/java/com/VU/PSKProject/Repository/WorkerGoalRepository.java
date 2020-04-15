package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.WorkerGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerGoalRepository extends JpaRepository<WorkerGoal, Long> {
}
