package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.TestEntity;
import com.VU.PSKProject.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<TestEntity> getAllTestEntities() {
        return testRepository.findAll();
    }

    public void createEntity(TestEntity testEntity) {
        testRepository.save(testEntity);
    }
}
