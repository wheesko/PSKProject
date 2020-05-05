package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Worker;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long>,
        CrudRepository<Worker, Long> {

    Worker findByManagedTeamId(Long managedTeamId);
    List<Worker> findByWorkingTeamId(Long workingTeamId);
}
