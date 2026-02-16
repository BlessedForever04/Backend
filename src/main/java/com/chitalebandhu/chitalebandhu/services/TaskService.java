package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Member;
import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.repository.MemberRepository;
import com.chitalebandhu.chitalebandhu.repository.TaskRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    public TaskService(TaskRepository taskRepository, MemberRepository memberRepository) {
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
    }

    public List<Tasks> getAllTasks(){
        return taskRepository.findAll();
    }

    public void addTask(Tasks task){
        Tasks savedTask = taskRepository.save(task);
        Member owner = memberRepository.findById(savedTask.getOwnerId()).orElseThrow(() -> new RuntimeException("Member not found"));
        owner.addTask(savedTask.getId());
        memberRepository.save(owner);
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

        if (newTask.getStatus() != null) {
            existingTask.get().setStatus(newTask.getStatus());
        }

        if (newTask.getOwnerId() != null) {
            existingTask.get().setOwnerId(newTask.getOwnerId());
        }

        if (newTask.getRemark() != null) {
            existingTask.get().setRemark(newTask.getRemark());
        }

      return taskRepository.save(existingTask.get());
    }


}
