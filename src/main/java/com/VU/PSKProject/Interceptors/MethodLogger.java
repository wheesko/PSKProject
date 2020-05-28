package com.VU.PSKProject.Interceptors;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.MethodJournalRecordService;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Service.WorkerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Aspect
@Configuration
public class MethodLogger {
    @Autowired
    MethodJournalRecordService methodJournalService;
    @Autowired
    UserService userService;
    @Autowired
    WorkerService workerService;

    @Around("execution(* com.VU.PSKProject.Controller.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.getUserByEmail(authentication.getName());

        Worker worker = workerService.getWorkerByUserId(user.getId());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        String[] classAndMethod = joinPoint.getSignature().toString().split(".* com\\.VU\\.PSKProject\\.Controller\\.");
        classAndMethod = classAndMethod[1].split("\\.");


        MethodJournalRecord methodJournal = new MethodJournalRecord();
        methodJournal.setUserNameSurname(worker.getName() + " " + worker.getSurname());
        methodJournal.setTime(formatter.format(date));
        methodJournal.setClassName(classAndMethod[0]);
        methodJournal.setMethodName(classAndMethod[1]);
        try{
            methodJournal.setTeamId(worker.getWorkingTeam().getId());
        }
        catch (Exception e){

        }


        methodJournalService.createRecord(methodJournal);

        Object obj = joinPoint.proceed();

        return obj;
    }
}
