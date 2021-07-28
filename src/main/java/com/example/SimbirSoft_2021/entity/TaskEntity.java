package com.example.SimbirSoft_2021.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="task_entity")
public class TaskEntity { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "release_id")
    private Long releaseId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private List<BoardEntity> boardEntities;

    public TaskEntity() { // конструктор
    }

    public TaskEntity(String taskName, String taskStatus, Long releaseId) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.releaseId = releaseId;
    }
    // ----------------------------------------------- гетеры и сетеры


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public List<BoardEntity> getBoardEntities() {
        return boardEntities;
    }

    public void setBoardEntities(List<BoardEntity> boardEntities) {
        this.boardEntities = boardEntities;
    }
}