package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>, CrudRepository<Role, Long> {
    boolean existsById(Long Id);

}
