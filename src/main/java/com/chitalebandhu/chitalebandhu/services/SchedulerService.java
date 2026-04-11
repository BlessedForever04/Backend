package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Notification;
import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private static final List<String> EXCLUDED_STATUSES = Arrays.asList("DONE", "COMPLETED", "OVERDUE");

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    private NotificationService notificationService;

    public SchedulerService(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    /**
     * Runs every day at midnight to mark overdue tasks/projects.
     * Also runs on application startup.
     */
    @Scheduled(cron = "0 0 0 * * *") // Every day at midnight
    public void markOverdueTasks() {
        logger.info("Running overdue task scheduler...");

        LocalDate today = LocalDate.now();
        List<Tasks> overdueTasks = taskRepository.findByDeadLineBeforeAndStatusNotIn(today, EXCLUDED_STATUSES);

        if (overdueTasks.isEmpty()) {
            logger.info("No overdue tasks found.");
            logger.info("What the hell is this?");
            return;
        }

        int updatedCount = 0;
        for (Tasks task : overdueTasks) {

            if(notificationService.isNotificationAlreadySent(task.getId(), "OVERDUE_ALERT")){
                continue;
            }

            task.setStatus("OVERDUE");
            taskRepository.save(task);

            //Create notifications when projects get overdued
            Notification notification = new Notification();

            notification.setMessage((task.getType().equals("TASK") ? "TASK" : "PROJECT") + " '" + task.getTitle() + "' is overdue!");
            notification.setTime(java.time.LocalDateTime.now());
            notification.setUserId(task.getOwnerId());
            notification.setIsRead(false);
            notification.setEventType("OVERDUE_ALERT");
            notification.setHelperId(task.getId());
            notification.setDeleted(false);

            notificationService.addNotification(notification);

            taskService.createOverdueActivity(task);

            updatedCount++;
            logger.debug("Marked task/project '{}' (ID: {}) as OVERDUE", task.getTitle(), task.getId());
        }

        logger.info("Marked {} tasks/projects as OVERDUE", updatedCount);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void overdueWarning(){

        Optional<List<Tasks>> tasks = taskRepository.findByDeadLineAfterAndStatusNot(LocalDateTime.now(), "DONE");

        if(tasks.isPresent()){
            List<Tasks> criticalTasks = new ArrayList<>();

            for (Tasks task : tasks.get()) {
                int totalDuration = (int) Math.ceil(ChronoUnit.DAYS.between(task.getStartDate(), task.getDeadline()));
                int remainingTime = (int) Math.ceil(ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline()));
                int tenPercent = (int) Math.ceil(totalDuration * 0.1);

                if (remainingTime <= tenPercent && remainingTime > 0) {
                    criticalTasks.add(task);
                }
            }

            for(Tasks task : criticalTasks){
                if(notificationService.isNotificationAlreadySent(task.getId(), "OVERDUE_WARNING")){
                    continue;
                }

                String type = (task.getType().equals("TASK") ? "TASK" : "PROJECT");
                Notification newNotification = new Notification();

                newNotification.setMessage(type + " '" + task.getTitle() + "' will reach deadline in next 5 days!");
                newNotification.setTime(LocalDateTime.now());
                newNotification.setUserId(task.getOwnerId());
                newNotification.setIsRead(false);
                newNotification.setEventType("OVERDUE_WARNING");
                newNotification.setHelperId(task.getId());
                newNotification.setDeleted(false);

                notificationService.addNotification(newNotification);
            }
        }
        else{
            logger.debug("No task with overdue condition.");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void taskReadyForWork(){
        LocalDate targetDay = LocalDate.now();
        LocalDateTime startOfDay = targetDay.atStartOfDay();
        LocalDateTime endOfDay = targetDay.atTime(23, 59, 59);

        Optional<List<Tasks>> tasks = taskRepository.findByStartDateBetween(startOfDay, endOfDay);

        if(tasks.isPresent()){
            for(Tasks task : tasks.get()){

                if(notificationService.isNotificationAlreadySent(task.getId(), "PROJECT_READY_TO_WORK")){
                    continue;
                }

                String type = (task.getType().equals("TASK") ? "TASK" : "PROJECT");
                Notification newNotification = new Notification();

                newNotification.setMessage(type + " '" + task.getTitle() + "' is active!");
                newNotification.setTime(LocalDateTime.now());
                newNotification.setUserId(task.getOwnerId());
                newNotification.setIsRead(false);
                newNotification.setEventType("PROJECT_READY_TO_WORK");
                newNotification.setHelperId(task.getId());
                newNotification.setDeleted(false);

                notificationService.addNotification(newNotification);
            }
        }
        else{
            logger.debug("No projects started today");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void taskReadyForWorkInFuture(){
        LocalDate targetDay = LocalDate.now().plusDays(7);
        LocalDateTime startOfDay = targetDay.atStartOfDay();
        LocalDateTime endOfDay = targetDay.atTime(23, 59, 59);

        Optional<List<Tasks>> tasks = taskRepository.findByStartDateBetween(startOfDay, endOfDay);

        if(tasks.isPresent()){
            for(Tasks task : tasks.get()){

                if(notificationService.isNotificationAlreadySent(task.getId(), "PROJECT_READY_TO_WORK")){
                    continue;
                }

                String type = (task.getType().equals("TASK") ? "TASK" : "PROJECT");
                Notification newNotification = new Notification();

                newNotification.setMessage(type + " '" + task.getTitle() + "' will be active in 7 days.");
                newNotification.setTime(LocalDateTime.now());
                newNotification.setUserId(task.getOwnerId());
                newNotification.setIsRead(false);
                newNotification.setEventType("PROJECT_READY_TO_WORK_FUTURE");
                newNotification.setHelperId(task.getId());
                newNotification.setDeleted(false);

                notificationService.addNotification(newNotification);
            }
        }
        else{
            logger.debug("No projects will start in 7 days.");
        }
    }

    /**
     * Runs on application startup to catch any overdue items immediately.
     */
    @Scheduled(initialDelay = 5000, fixedDelay = Long.MAX_VALUE)
    public void markOverdueTasksOnStartup() {
        logger.info("Checking for overdue tasks on startup...");
        markOverdueTasks();
        overdueWarning();
        taskReadyForWork();
        taskReadyForWorkInFuture();
    }
}