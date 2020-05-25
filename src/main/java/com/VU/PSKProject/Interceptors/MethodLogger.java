package com.VU.PSKProject.Interceptors;

import com.VU.PSKProject.Entity.MethodJournalRecord;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.MethodJournalRecordService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Aspect
@Configuration
public class MethodLogger {
    @Autowired
    MethodJournalRecordService methodJournalService;

    @Around("execution(* com.VU.PSKProject.Controller.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable  {

        //need to somehow get currently logged in user
        Worker worker = new Worker();
        worker.setName("guy");
        worker.setSurname("faker");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        String[] classAndMethod = joinPoint.getSignature().toString().split(".* com\\.VU\\.PSKProject\\.Controller\\.");
        classAndMethod = classAndMethod[1].split("\\.");

        Object obj = joinPoint.proceed();

        MethodJournalRecord methodJournal = new MethodJournalRecord();
        methodJournal.setUser(worker.getName() + " " + worker.getSurname());
        methodJournal.setTime(formatter.format(date));
        methodJournal.setClassName(classAndMethod[0]);
        methodJournal.setMethodName(classAndMethod[1]);

        methodJournalService.createRecord(methodJournal);
        return obj;
    }
}
