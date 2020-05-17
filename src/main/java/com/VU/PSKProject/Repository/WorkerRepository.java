package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>,
        CrudRepository<Worker, Long> {

    Optional<Worker> findByManagedTeamId(Long managedTeamId);
    List<Worker> findByWorkingTeamId(Long workingTeamId);

    @Query("select w from worker w join learning_day d on w.id = d.assignee.id" +
            " where d.topic.id = :topicId" +
            " AND d.dateTimeAt <  CURRENT_TIMESTAMP ")
    List<Worker> getWorkersByTopicId(Long topicId);
}
