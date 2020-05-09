package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.RoleGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleGoalRepository extends JpaRepository<RoleGoal, Long> {
}
