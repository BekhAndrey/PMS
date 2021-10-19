package com.example.lab6;

import java.io.Serializable;
import java.util.Random;

public class Task implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private String difficulty;
    private String priority;
    private String duration;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private String imagePath = "/storage/emulated/0/Pictures/IMG_20210929_052107.jpg";

    public Task(){
        this.id =  new Random().nextInt(10000);
        this.status = "Unfinished";
    }
    public Task(String name, String description, String difficulty, String priority, String duration, String imagePath) {
        this.status = "Unfinished";
        this.id =  new Random().nextInt(10000);
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.priority = priority;
        this.duration = duration;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", priority='" + priority + '\'' +
                ", duration='" + duration + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
