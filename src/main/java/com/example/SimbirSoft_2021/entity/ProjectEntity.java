package com.example.SimbirSoft_2021.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="project_entity")
public class ProjectEntity { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_status")
    private String projectStatus;

    @Column(name = "release_id")
    private Long releaseId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<TaskEntity> taskEntities;

    public ProjectEntity(){ // конструктор
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

    public List<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }
}