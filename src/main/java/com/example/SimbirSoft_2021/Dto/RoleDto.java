package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Роли человека")
public class RoleDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id выбранной роли")
    private Long roleId;

    @Schema(description = "Имя роли")
    private String roleName;

    @Schema(description = "Id закреплённой доски, к которой указываем роли")
    private Long taskId;

    @Schema(description = "Id человека, которого мы берём")
    private Long userId;

    public RoleDto() { // конструктор
    }

    public RoleDto(String roleName, Long taskId, Long userId) {
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
