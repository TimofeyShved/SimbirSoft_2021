package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Роли")
public class RoleDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id выбранной роли")
    private Long roleId;

    @Schema(description = "Имя роли")
    private String roleName;

    @Schema(description = "Id закреплённой доски, к которой указываем роли")
    private Long boardId;

    @Schema(description = "Id человека, которого мы берём")
    private Long userId;

    public RoleDto() { // конструктор
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

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
