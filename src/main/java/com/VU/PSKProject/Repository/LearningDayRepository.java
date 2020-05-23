package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningDayRepository extends JpaRepository<LearningDay, Long> {
    List<LearningDay> findAllByAssigneeId(Long workerId);

    List<LearningDay> findAllByDateTimeAtBetweenAndAssigneeId(Timestamp dateFrom, Timestamp dateTo, Long workerId);

    List<LearningDay> findAllByAssigneeIdIn(List<Long> workerIds);


    @Query("select d.assignee from learning_day d" +
            " where d.topic.id = :topicId" +
            " AND d.dateTimeAt <  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdPast(@Param("topicId") Long topicId);

    @Query("select d.assignee from learning_day d" +
            " where d.topic.id in :topicIds" +
            " AND d.dateTimeAt <  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdsPast(@Param("topicIds")List<Long> topicIds);

    @Query("select d.assignee from learning_day d" +
            " where d.topic.id in :topicIds" +
            " AND d.dateTimeAt >  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdsFuture(@Param("topicIds")List<Long> topicIds);

    @Query("select d.topic from learning_day  d" +
            " where d.dateTimeAt < CURRENT_TIMESTAMP" +
            " AND d.assignee.workingTeam.id = :teamId")
    List<Topic> findTopicsByTeamPAST(@Param("teamId") Long teamId);

    @Query("select d.topic from learning_day  d" +
            " where d.dateTimeAt > CURRENT_TIMESTAMP" +
            " AND d.assignee.workingTeam.id = :teamId")
    List<Topic> findTopicsByTeamFuture(@Param("teamId") Long teamId);

    @Query("select d.topic from learning_day  d" +
            " where d.dateTimeAt < CURRENT_TIMESTAMP" +
            " AND d.assignee.id = :workerId")
    List<Topic> findTopicsByWorkerPAST(@Param("workerId") Long workerId);

    @Query("select d.topic from learning_day  d" +
            " where d.dateTimeAt > CURRENT_TIMESTAMP" +
            " AND d.assignee.id = :workerId")
    List<Topic> findTopicsByWorkerFuture(@Param("workerId") Long workerId);
}
