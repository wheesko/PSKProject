package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.*;
import com.VU.PSKProject.Repository.WorkerRepository;
import com.VU.PSKProject.Service.CSVExporter.CSVExporter;
import com.VU.PSKProject.Service.Exception.WorkerException;
import com.VU.PSKProject.Service.MailerService.EmailServiceImpl;
import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Mapper.TopicMapper;
import com.VU.PSKProject.Service.Mapper.UserMapper;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.Model.Worker.*;
import com.VU.PSKProject.Service.Model.WorkerRegisterDTO;
import com.VU.PSKProject.Utils.EventDate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    private final class WorkerServiceException extends RuntimeException {
        WorkerServiceException(String message) {
            super(message);
        }
    }

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private LearningDayMapper learningDayMapper;

    @Autowired
    private WorkerGoalService workerGoalService;

    @Autowired
    private TopicMapper topicMapper;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorker(Long id) {
        return workerRepository.findById(id);
    }

    @Transactional
    public void createWorker(Worker worker) {
        if (worker.getQuarterLearningDayLimit() == 0)
            worker.setQuarterLearningDayLimit(Integer.parseInt(Objects.requireNonNull(env.getProperty("worker.defaultQuarterLearningDayLimit"))));
        if (worker.getConsecutiveLearningDayLimit() == 0)
            worker.setConsecutiveLearningDayLimit(Integer.parseInt(Objects.requireNonNull(env.getProperty("worker.defaultConsecutiveLearningDayLimit"))));
        workerRepository.save(worker);
    }

    public ResponseEntity<WorkerToGetDTO> getWorkerById(Long id, UserDTO user) {
        Optional<Worker> worker = getWorker(id);
        if(worker.isPresent()) {
            WorkerToGetDTO workerDTO = workerMapper.workerToGetDTO(worker.get());
            workerDTO.setEmail(worker.get().getUser().getEmail());
            workerDTO.setLearningDays(learningDayService.getAllLearningDaysByWorkerId(worker.get().getId()).stream()
                    .map(learningDayMapper::toDTO)
                    .collect(Collectors.toList())
            );
            workerDTO.setGoals(worker.get().getGoals().stream()
                    .map(goal -> topicMapper.toReturnDto(workerGoalService.getWorkerGoal(goal.getId()).get().getTopic()))
                    .collect(Collectors.toList()));

            workerDTO.setManager(workerMapper.toGetDTOManagerDTO(worker.get().getWorkingTeam().getManager()));
            workerDTO.getManager().setEmail(worker.get().getWorkingTeam().getManager().getUser().getEmail());



            if(checkWorkerLeadRelationship(getWorkerByUserId(user.getId()), worker.get()))
                return ResponseEntity.ok(workerDTO);
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Worker with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    public void updateWorker(Long id, Worker worker) {
        try {
            if (workerRepository.findById(id).isPresent()) {
                worker.setId(id);
                workerRepository.save(worker);
            }
        } catch (OptimisticLockException e) {
            throw new WorkerException("This worker was recently modified.");
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
        if (workerDTO.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("No email provided!");
        }

        return ResponseEntity.ok().build();
    }

    public List<Worker> getWorkersByTopic(Long topicId) {
        return learningDayService.getAssigneesByTopicIdPast(topicId);
    }

    public List<Worker> getWorkersByIds(List<Long> ids) {
        return learningDayService.getAssigneesByTopicIdsPast(ids);
    }

    public List<Worker> getWorkersByTopicsTeamManager(Long teamId, List<Long> ids, Worker manager, EventDate.eventDate time) {
        List<Worker> workers = new ArrayList<>();
        List<Worker> allWorkers = null;
        if (time.equals(EventDate.eventDate.PAST))
            allWorkers = learningDayService.getAssigneesByTopicIdsPast(ids);
        if (time.equals(EventDate.eventDate.FUTURE))
            allWorkers = learningDayService.getAssigneesByTopicIdsFuture(ids);

        for (Worker w : allWorkers) {
            if (!workers.contains(w) && teamId.equals(manager.getManagedTeam().getId()) && teamId.equals(w.getWorkingTeam().getId())) {
                workers.add(w);
            }
        }
        return workers;
    }

    public List<WorkerToGetDTO> extractByManager(List<Worker> workers, Worker manager) {
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();

        for (Worker w : workers) {
            if (w.getWorkingTeam().getId().equals(manager.getManagedTeam().getId())) {
                workerDTOS.add(workerMapper.toDTO(w));
            }
        }
        return workerDTOS;
    }

    public List<WorkerToGetDTO> retrieveAllWorkers() {
        List<Worker> workers = getAllWorkers();
        List<WorkerToGetDTO> workerDTOS = new ArrayList<>();
        for (Worker w : workers) {
            workerDTOS.add(workerMapper.toDTO(w));
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

    private ResponseEntity<String> sendEmailToNewWorker(User u, Worker w, String tempPassword) {
        String subject = "PSK_123 New worker";
        String text = String.format("Hello,\n You've been invited to join %s. You can finish your registration by logging " +
                "in to the application with your email address and temporary password.\n" +
                "Temporary password: %s.\n Hope to see you soon!\n PSK_123", w.getWorkingTeam().getName(), tempPassword);
        try {
            emailService.sendMessage(u.getEmail(), subject, text);
            return ResponseEntity.ok("An email to the new worker has been sent!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Failed to send email to the worker!");
        }
    }

    public ResponseEntity<String> createFreshmanWorker(WorkerToCreateDTO workerDTO, Principal principal) {

        if (userService.getUserByEmail(workerDTO.getEmail()).getEmail() != null)
            throw new WorkerServiceException("Email already taken");

        String temporaryPassword = RandomStringUtils.randomAlphanumeric(7);
        Worker worker = workerMapper.fromDTO(workerDTO);
        User u = userService.createUser(workerDTO.getEmail(), temporaryPassword);
        worker.setUser(u);
        Worker managerWorker = getWorkerByUserId(userService.getUserByEmail(principal.getName()).getId());
        teamService.getTeamByManager(managerWorker.getId()).ifPresent(worker::setWorkingTeam);
        if (workerDTO.getRole() == null || workerDTO.getRole().getRoleName().length() == 0)
            throw new WorkerException("Role not provided");
        Role workerRole = roleService.findOrCreateRole(workerDTO.getRole().getRoleName());
        worker.setRole(workerRole);
        createWorker(worker);
        return sendEmailToNewWorker(u, worker, temporaryPassword);
    }

    public boolean checkWorkerLeadRelationship(Worker lead, Worker worker) {
        if (worker.getWorkingTeam().getManager().getId().equals(lead.getId()))
            return true;

        Worker tempManager = worker.getWorkingTeam().getManager();
        if (tempManager.getWorkingTeam().getId().equals(tempManager.getManagedTeam().getId()))
            return false;

        return checkWorkerLeadRelationship(lead, tempManager);
    }
}
