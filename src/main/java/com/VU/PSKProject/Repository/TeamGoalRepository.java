package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.TeamGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamGoalRepository extends JpaRepository<TeamGoal, Long>,
        CrudRepository<TeamGoal, Long> {

    List<TeamGoal> findByTopicId(Long topicId);
}
