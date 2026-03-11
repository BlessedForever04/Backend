package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.entity.Activity;
import com.chitalebandhu.chitalebandhu.services.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activity")
public class ActivityController {
    private final ActivityService activityServic;

    public ActivityController(ActivityService activityServic){
        this.activityServic = activityServic;
    }

    @GetMapping("activities")
    public List<Activity> getAllActivities(){
        return activityServic.getAllActivities();
    }

    @PostMapping("add")
    public void addActivity(@RequestBody Activity newActivity){
        activityServic.addActivity(newActivity);
    }

    @DeleteMapping("delete")
    public void deleteActivity(@RequestBody String id){
        activityServic.removeActivity(id);
    }

    @PutMapping("update/{activityId}")
    public void updateActivity(@PathVariable String activityId, @RequestBody Activity newActivity){
        activityServic.updateActivity(activityId, newActivity);
    }
}
