package com.example.SimbirSoft_2021.entity;

import javax.persistence.*;

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

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "user_id")
    private Long userId;

    public RoleEntity() { // конструктор
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
