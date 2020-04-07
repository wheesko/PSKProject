package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TestEntity;
import com.VU.PSKProject.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestEntityController {
    @Autowired
    private TestService testService;

    @GetMapping("/allEntities")
    private List<TestEntity> getAllEntities() {
        return testService.getAllTestEntities();
    }

    @PutMapping("/createEntity")
    private void createEntity(@RequestBody TestEntity testEntity) {
        testEntity.setData("Test entity created");
        testService.createEntity(testEntity);
    }
}
