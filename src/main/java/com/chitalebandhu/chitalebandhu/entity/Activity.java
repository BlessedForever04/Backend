package com.chitalebandhu.chitalebandhu.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "activity")
public class Activity {
    @Id
    private String id;
    private String userName; // Ram, sham
    private String verb; // created, started, completed, deleted, submitted review
    private String projectName; // Improving the marketing strategy
    private LocalDate time;
    // final activity = Ram created Improving the marketing strategy - 2 hours/days/months ago

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
