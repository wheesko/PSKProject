package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>,
        CrudRepository<Worker, Long> {

    Optional<Worker> findByManagedTeamId(Long managedTeamId);
    List<Worker> findByWorkingTeamId(Long workingTeamId);

    List<Worker>findAllByWorkingTeamId(Long workingTeamId);
    List<Worker>findAllByManagedTeamId(Long managedTeamId);

    boolean existsById(Long Id);

    Optional<Worker> findByUserId(Long id);
}
