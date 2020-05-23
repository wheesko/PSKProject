package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LearningDayRepository extends JpaRepository<LearningDay, Long> {
    List<LearningDay> findAllByAssigneeId(Long workerId);

    List<LearningDay> findAllByDateTimeAtBetweenAndAssigneeId(Timestamp dateFrom, Timestamp dateTo, Long workerId);

    List<LearningDay> findAllByAssigneeIdIn(List<Long> workerIds);


    @Query("select d.assignee from learning_day d" +
            " where d.topic.id = :topicId" +
            " AND d.dateTimeAt <  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdPast(Long topicId);

    @Query("select d.assignee from learning_day d" +
            " where d.topic.id in :topicIds" +
            " AND d.dateTimeAt <  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdsPast(@Param("topicIds")List<Long> topicIds);

    @Query("select d.assignee from learning_day d" +
            " where d.topic.id in :topicIds" +
            " AND d.dateTimeAt >  CURRENT_TIMESTAMP ")
    List<Worker> findAssigneesByTopicIdsFuture(@Param("topicIds")List<Long> topicIds);
}
