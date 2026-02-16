package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.DTOs.TaskDTO;
import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("member/{ownerId}")
    public ResponseEntity<List<Tasks>> getTaskByOwner(@PathVariable  String ownerId){
        try{
            List<Tasks> tasks = taskService.getTaskByOwner(ownerId);
            return new ResponseEntity<>(tasks , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable String id){
        try{
            Tasks task = taskService.getTaskById(id);
            return new ResponseEntity<>(task , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND );
        }
    }

    @PutMapping("update/{Id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable String Id, @RequestBody Tasks newTask){
       Tasks task =  taskService.updateTaskById(Id, newTask);
       if(task != null) return new  ResponseEntity<>(task , HttpStatus.OK);
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete/{Id}")
    public void deleteTask(@PathVariable String Id){
        taskService.deleteTaskById(Id);
    }
}
