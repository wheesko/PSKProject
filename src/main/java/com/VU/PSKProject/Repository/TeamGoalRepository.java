package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.TeamGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamGoalRepository extends JpaRepository<TeamGoal, Long> {
}
