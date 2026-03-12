package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
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

    public long getCountByPriority(String priority){
        return taskRepository.countByPriority(priority);
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

    public long getTaskCountByParentTaskIdAndStatus(String parentTaskId, String status){
        return taskRepository.countByParentTaskIdAndStatus(parentTaskId, status);
    }

    public void updateStatusById(String id, String status){
        Optional<Tasks> existingTask = taskRepository.findById(id);
        if(existingTask.isPresent()){
            existingTask.get().setStatus(status);
        }
        else{
            return;
        }
        taskRepository.save(existingTask.get());
    }

    public long getAllTaskCountByType(String type){
        return taskRepository.countByType(type);
    }

    public List<Tasks> getAllTasksByType(String type){
        Optional <List<Tasks>> allProjects = taskRepository.findByType(type);
        return allProjects.orElse(null);
    }
}
