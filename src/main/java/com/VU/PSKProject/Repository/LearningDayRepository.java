package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningDayRepository extends JpaRepository<LearningDay, Long> {
    @Query("select le from learning_day le where le.assignee.id = :workerId")
    List<LearningDay> findAllLearningDaysByWorkerId(@Param("workerId") Long workerId);

    @Query("select f from learning_day f where f.assignee.id in :workerIds")
    List<LearningDay> findByWorkerIdIn(@Param("workerIds") List<Long> workerIds);
}
