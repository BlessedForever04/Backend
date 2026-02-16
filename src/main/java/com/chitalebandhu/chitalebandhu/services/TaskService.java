package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Member;
import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.exceptions.ResourceNotFoundException;
import com.chitalebandhu.chitalebandhu.repository.MemberRepository;
import com.chitalebandhu.chitalebandhu.repository.TaskRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return taskRepository.findByOwnerId(ownerId);
    }

    public void deleteTaskById(String id){
        taskRepository.deleteById(id);
    }

    public void updateTaskById(String id, @NonNull Tasks newTask){
        Tasks existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setTitle(newTask.getTitle());
        existingTask.setDescription(newTask.getDescription());
        existingTask.setStatus(newTask.getStatus());
        existingTask.setOwnerId(newTask.getOwnerId());
        existingTask.setRemark(newTask.getRemark());
    }

}
