package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Service.CSVExporter.CSVExporter;
import com.VU.PSKProject.Service.MailerService.EmailServiceImpl;
import com.VU.PSKProject.Service.Mapper.UserMapper;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.UserToRegisterDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.VU.PSKProject.Service.Model.WorkerRegisterDTO;
import com.VU.PSKProject.Utils.EventDate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorker(Long id) {
        return workerRepository.findById(id);
    }

    @Transactional
    public void createWorker(Worker worker) {
        if(worker.getQuarterLearningDayLimit() == 0)
            worker.setQuarterLearningDayLimit(Integer.parseInt(Objects.requireNonNull(env.getProperty("worker.defaultQuarterLearningDayLimit"))));
        if(worker.getConsecutiveLearningDayLimit() == 0)
            worker.setConsecutiveLearningDayLimit(Integer.parseInt(Objects.requireNonNull(env.getProperty("worker.defaultConsecutiveLearningDayLimit"))));
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

    public ResponseEntity<String> validateWorkerData(WorkerToCreateDTO workerDTO) {
        if(workerDTO.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("No email provided!");
        }

        return ResponseEntity.ok().build();
    }
    public List<Worker> getWorkersByTopic(Long topicId) {
        return learningDayService.getAssigneesByTopicIdPast(topicId);
    }

    public List<Worker> getWorkersByIds(List<Long> ids){
        return learningDayService.getAssigneesByTopicIdsPast(ids);
    }

    public List<Worker> getWorkersByTopicsTeamManager(Long teamId, List<Long> ids, Worker manager, EventDate.eventDate time){
        List<Worker> workers = new ArrayList<>();
        List <Worker> allWorkers = null;
        if (time.equals(EventDate.eventDate.PAST))
            allWorkers = learningDayService.getAssigneesByTopicIdsPast(ids);
        if (time.equals(EventDate.eventDate.FUTURE))
             allWorkers = learningDayService.getAssigneesByTopicIdsFuture(ids);

        for (Worker w: allWorkers) {
            if(!workers.contains(w) && teamId.equals(manager.getManagedTeam().getId()) && teamId.equals(w.getWorkingTeam().getId())){
                workers.add(w);
            }
        }
        return workers;
    }
    public List<WorkerToGetDTO> extractByManager(List<Worker> workers, Worker manager){
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();

        for (Worker w: workers) {
            if(w.getWorkingTeam().getId().equals(manager.getManagedTeam().getId())){
                WorkerToGetDTO workerDTO = workerMapper.toGetDTO(w);
                workerDTO.setEmail(w.getUser().getEmail());
                workerDTO.setManagerId(manager.getId());
                workerDTOS.add(workerDTO);
            }
        }
        return workerDTOS;
    }

    public List<WorkerToGetDTO> retrieveAllWorkers() {
        List<Worker> workers = getAllWorkers();
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();
        for (Worker w: workers) {
            WorkerToGetDTO workerDTO = workerMapper.toGetDTO(w);
            workerDTO.setEmail(w.getUser().getEmail());
            workerDTO.setManagerId(teamService.getTeam(workerDTO.getWorkingTeam().getId()).get().getManager().getId());
            workerDTOS.add(workerDTO);
        }
        return workerDTOS;
    }

    public Worker getWorkerByUserId(Long userId) {
        return workerRepository.findByUserId(userId).orElse(new Worker());
    }

    public UserToRegisterDTO registerWorker(UserDTO userDTO, WorkerRegisterDTO workerRegisterDTO) {
        Worker worker = getWorkerByUserId(userDTO.getId());
        worker.setName(workerRegisterDTO.getName());
        worker.setSurname(workerRegisterDTO.getSurname());
        // set UserAuthority to WORKER
        User user = userMapper.fromDTO(userDTO);
        user.setUserAuthority(UserAuthority.WORKER);
        user.setPassword(passwordEncoder.encode(workerRegisterDTO.getPassword()));
        userService.updateUser(user);
        workerRepository.save(worker);
        UserToRegisterDTO updatedWorkerDTO = workerMapper.toRegisterDTO(worker);
        updatedWorkerDTO.setEmail(worker.getUser().getEmail());
        updatedWorkerDTO.setUserAuthority(UserAuthority.WORKER);
        return updatedWorkerDTO;
    }
    public void exportToCSV(List<WorkerToExportDTO> dataToExport, HttpServletResponse response) throws Exception {
        String[] headers = {"Name,", "Surname,", "Email,", "Role,", "Team,", "Managed team\n"};
        CSVExporter.buildExportToCSVResponse(dataToExport, headers, response);
    }

    private ResponseEntity<String> sendEmailToNewWorker(User u, Worker w, String tempPassword){
        String subject = "PSK_123 New worker";
        String text = String.format("Hello,\n You've been invited to join %s. You can finish your registration by logging " +
                "in to the application with your email address and temporary password.\n" +
                "Temporary password: %s.\n Hope to see you soon!\n PSK_123", w.getWorkingTeam().getName(), tempPassword);
        try {
            emailService.sendMessage(u.getEmail(), subject, text);
            return ResponseEntity.ok("An email to the new worker has been sent!");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Failed to send email to the worker!");
        }
    }

    public ResponseEntity<String> createFreshmanWorker(WorkerToCreateDTO workerDTO, Principal principal){
        String temporaryPassword = RandomStringUtils.randomAlphanumeric(7);
        Worker worker = workerMapper.fromDTO(workerDTO);
        User u = userService.createUser(workerDTO.getEmail(), temporaryPassword);
        worker.setUser(u);
        if(workerDTO.getManagerId()!= null)
            teamService.getTeamByManager(workerDTO.getManagerId()).ifPresent(worker::setWorkingTeam);
        else
            teamService.getTeamByManager(userService.getUserByEmail(principal.getName()).getId()).ifPresent(worker::setWorkingTeam);
        createWorker(worker);

        return sendEmailToNewWorker(u, worker, temporaryPassword);
    }
}
