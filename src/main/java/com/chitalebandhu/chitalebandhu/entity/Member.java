package com.chitalebandhu.chitalebandhu.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "members")
public class Member {
    @Id
    private String id;
    private String name;

    private String email;
    private String mobileNo;
    private String role;
    private List<String> tasks = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addTask(String taskId){
        this.tasks.add(taskId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        if(tasks == null){
            this.tasks = new ArrayList<>();
        }else{
            this.tasks = tasks;
        }
    }

    public void removeTask(String taskId){
        this.tasks.remove(taskId);
    }
}
