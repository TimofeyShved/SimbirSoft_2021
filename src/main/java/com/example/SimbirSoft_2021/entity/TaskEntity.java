package com.example.SimbirSoft_2021.entity;

import com.example.SimbirSoft_2021.enumertion.StatusEnum;
import com.example.SimbirSoft_2021.exception.StatusEnumException;

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
    @Enumerated(EnumType.STRING)
    private StatusEnum taskStatus;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "release_id")
    private Long releaseId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private List<RoleEntity> roleEntities;

    public TaskEntity() { // конструктор
    }

    public TaskEntity(String taskName, StatusEnum taskStatus, Long projectId, Long releaseId) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.projectId = projectId;
        this.releaseId = releaseId;
    }

    public TaskEntity(Long taskId, String taskName, StatusEnum taskStatus, Long projectId, Long releaseId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.projectId = projectId;
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

    public StatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(StatusEnum taskStatus) throws StatusEnumException {
        try {
            this.taskStatus = taskStatus;
        }catch (Exception e){
            throw new StatusEnumException();
        }
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public List<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }
}