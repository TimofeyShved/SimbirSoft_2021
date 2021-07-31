package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;


@Schema(description = "Задачи")
public class TaskDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id задачи")
    private Long taskId;

    @Schema(description = "Имя задачи")
    private String taskName;

    @Schema(description = "Статус задачи")
    private String taskStatus;

    @Schema(description = "Id реализации нашей задачи")
    private Long releaseId;

    public TaskDto() { // конструктор
    }

    public TaskDto(String taskName, String taskStatus, Long releaseId) {
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
}