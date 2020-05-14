package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserService userService;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorker(Long id) {
        return workerRepository.findById(id);
    }

    public void createWorker(Worker worker) {
        //String randomPassword = PasswordUtils.generateRandomString(7);
        //TODO: remove temporaryPassword in final version (When predefined data is not needed)
        String temporaryPassword = "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG"; // encoded "admin" string
        User u = new User(worker.getName(), temporaryPassword, UserAuthority.WORKER);
        userService.createUser(u);
        worker.setUser(u);

        workerRepository.save(worker);
    }

    public void updateWorker(Long id, Worker worker) {
        // calling save() on an object with predefined id will update the corresponding database record
        // rather than inserting a new one
        if (workerRepository.findById(id).isPresent()){
            worker.setId(id);
            workerRepository.save(worker);
        }
    }

    public void deleteWorker(Long id) {
        if (id != null)
            workerRepository.deleteById(id);
    }

    public Optional<Worker> findByManagedTeamId(Long id) {
        return workerRepository.findByManagedTeamId(id);
    }

    public List<Worker> findByWorkingTeamId(Long id) {
        return workerRepository.findByWorkingTeamId(id);
    }
}
