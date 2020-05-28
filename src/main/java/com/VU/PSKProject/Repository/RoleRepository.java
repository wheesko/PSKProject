package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, CrudRepository<Role, Long> {
    boolean existsById(Long Id);
    Optional<Role> findByName(String roleName);
}
