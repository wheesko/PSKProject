package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, CrudRepository<Role, Long> {
    boolean existsById(Long Id);
}
