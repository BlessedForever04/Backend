package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
import com.chitalebandhu.chitalebandhu.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("allTasks/{type}")
    public List<Tasks> getAllProject(@PathVariable String type){return taskService.getAllTasksByType(type);}

    @PostMapping("add")
    public void addTask(@RequestBody Tasks task){
        taskService.addTask(task);
    }

    @GetMapping("member/{ownerId}")
    public ResponseEntity<List<Tasks>> getTaskByOwner(@PathVariable String ownerId){
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

    @GetMapping("TaskCount/{type}")
    public long getAllTaskCount(@PathVariable String type){
        return taskService.getAllTaskCountByType(type);
    }

    @GetMapping("TodoCount/{parentTaskId}/{status}")
    public long getCountTodo(@PathVariable String parentTaskId, @PathVariable String status){
        return taskService.getTaskCountByParentTaskIdAndStatus(parentTaskId, status);
    }
}
