package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.List;

@Schema(description = "Проект")
public class ProjectDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id выбранного проекта")
    private Long projectId;

    @Schema(description = "Имя проекта")
    private String projectName;

    @Schema(description = "Статус проекта")
    private String projectStatus;

    @Schema(description = "Id реализации нашего проекта")
    private Long releaseId;


    public ProjectDto(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

}