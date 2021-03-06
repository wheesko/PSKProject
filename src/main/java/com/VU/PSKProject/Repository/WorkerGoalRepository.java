package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Entity.WorkerGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerGoalRepository extends JpaRepository<WorkerGoal, Long> {
    @Query("select g from worker_goal g where g.worker.id in :workerIds")
    List<WorkerGoal> findByWorkerIdIn(@Param("workerIds") List<Long> workerIds);

    @Query("select g.worker from worker_goal g where g.topic.id in :topicIds")
    List<Worker> findWorkersByTopicIds(@Param("topicIds") List<Long> topicIds);
}
