package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Schema(description = "Доска с проектами и задачами")
public class BoardDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id доски")
    //@Null(groups = {New.class})
    //@NotNull(groups = {UpdateName.class})
    private Long boardId;

    @Schema(description = "Id выбранного проекта")
    private Long projectId;

    @Schema(description = "Id выбранной задачи")
    private Long taskId;

    public BoardDto() { // конструктор
    }

    public BoardDto(Long projectId, Long taskId) {
        this.projectId = projectId;
        this.taskId = taskId;
    }

    // ----------------------------------------------- гетеры и сетеры

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}