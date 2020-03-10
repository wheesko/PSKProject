package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
