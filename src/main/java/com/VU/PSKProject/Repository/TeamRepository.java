package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  TeamRepository extends JpaRepository<Team, Long>, CrudRepository<Team, Long> {
    @Query("select f from team f where f.manager.id = :managerId")
    Optional<Team> findByManagerId(@Param("managerId") Long managerId);

    boolean existsById(Long Id);
}
