package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(description = "Доска с проектами и задачами")
public class BoardDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id доски")
    private Long boardId;

    @Schema(description = "Id выбранного проекта")
    private Long projectId;

    @Schema(description = "Id выбранной задачи")
    private Long taskId;

    public BoardDto() { // конструктор
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