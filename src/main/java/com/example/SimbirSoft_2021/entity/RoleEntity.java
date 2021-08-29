package com.example.SimbirSoft_2021.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="role_entity")
public class RoleEntity { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "user_id")
    private Long userId;

    public RoleEntity() { // конструктор
    }

    public RoleEntity(String roleName, Long taskId, Long userId) {
        this.roleName = roleName;
        this.taskId = taskId;
        this.userId = userId;
    }

    public RoleEntity(Long roleId, String roleName, Long taskId, Long userId) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.taskId = taskId;
        this.userId = userId;
    }

    // ----------------------------------------------- гетеры и сетеры

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
