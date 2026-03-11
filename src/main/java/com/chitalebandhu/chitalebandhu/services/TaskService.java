package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.repository.MemberRepository;
import com.chitalebandhu.chitalebandhu.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Tasks task){
        Tasks savedTask = taskRepository.save(task);
    }

    public Tasks getTaskById(String id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Tasks> getTaskByOwner(String ownerId){
        Optional<List<Tasks>> tasks = taskRepository.findByOwnerId(ownerId);
        return tasks.orElse(null);
    }

    public void deleteTaskById(String id){
        taskRepository.deleteById(id);
    }

    public Tasks updateTaskById(String id, Tasks newTask) {
        Optional<Tasks> existingTask = taskRepository.findById(id);

        if(existingTask.isEmpty()){
            return null;
        }

        if (newTask.getTitle() != null && !newTask.getTitle().trim().isEmpty()) {
            existingTask.get().setTitle(newTask.getTitle());
        }

        if (newTask.getDescription() != null && !newTask.getDescription().trim().isEmpty()) {
            existingTask.get().setDescription(newTask.getDescription());
        }

        if (newTask.getStatus() != null && !newTask.getStatus().trim().isEmpty()) {
            existingTask.get().setStatus(newTask.getStatus());
        }

        if (newTask.getOwnerId() != null && !newTask.getOwnerId().trim().isEmpty()) {
            existingTask.get().setOwnerId(newTask.getOwnerId());
        }

        if (newTask.getRemark() != null && !newTask.getRemark().trim().isEmpty()) {
            existingTask.get().setRemark(newTask.getRemark());
        }

      return taskRepository.save(existingTask.get());
    }


    public long getTaskCountOfStatusNotStarted(String parentTaskId){
        return taskRepository.countByParentTaskIdAndStatus(parentTaskId, "DONE");
    }

    public long getTaskCountOfStatusTodo(String parentTaskId){
        return taskRepository.countByParentTaskIdAndStatus(parentTaskId, "TODO");
    }

    public long getTaskCountOfStatusDone(String parentTaskId){
        return taskRepository.countByParentTaskIdAndStatus(parentTaskId, "NOT_STARTED");
    }

    public long getTaskCountOfStatusOverDue(String parentTaskId){
        return taskRepository.countByParentTaskIdAndStatus(parentTaskId, "OVERDUE");
    }

    public long getAllTaskCount(){
        return taskRepository.countByType("TASK");
    }

    public long getAllProjectCount(){
        return taskRepository.countByType("PROJECT");
    }

    public List<Tasks> getAllProjects(){
        Optional <List<Tasks>> allProjects = taskRepository.findByType("PROJECT");
        return allProjects.orElse(null);
    }

    public List<Tasks> getAllTasks(){
        Optional <List<Tasks>> allTasks = taskRepository.findByType("TASK");
        return allTasks.orElse(null);
    }

}
