package com.VU.PSKProject.Repository;

import com.VU.PSKProject.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends JpaRepository<Topic,Long>, CrudRepository<Topic, Long> {
    boolean existsById(Long Id);
}
