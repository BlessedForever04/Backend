package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.DTOs.TaskDTO;
import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    // This is just for testing, we dont need this is production
    @GetMapping("AllTasks")
    public List<Tasks> getAllTasks(){
        return taskService.getAllTasks();
    }

    // I dont know why its nont working, have to fix this
//    @PutMapping("add")
//    public ResponseEntity <TaskDTO> addTask(@RequestBody Tasks task){
//        Tasks savedTask = taskService.addTask(task);
//        TaskDTO taskDto = modelMapper.map(savedTask, TaskDTO.class);
//        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
//    }

    // This is working but we dont want this shit
    @PostMapping("add")
    public void addTask(@RequestBody Tasks task){
        taskService.addTask(task);
    }

    @GetMapping("{ownerId}/getTasks")
    public List<Tasks> getTaskByOwner(String Id){
        return taskService.getTaskByOwner(Id);
    }

    @GetMapping("id/{Id}")
    public Tasks getTaskById(@PathVariable String Id){
        return taskService.getTaskById(Id);
    }

    @PutMapping("update/{Id}")
    public void updateTask(@PathVariable String Id, @RequestBody Tasks newTask){
        taskService.updateTaskById(Id, newTask);
    }

    @DeleteMapping("delete/{Id}")
    public void deleteTask(@PathVariable String Id){
        taskService.deleteTaskById(Id);
    }
}
