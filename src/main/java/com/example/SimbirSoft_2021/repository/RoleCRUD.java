package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleCRUD extends CrudRepository<RoleEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    // @Repository
//public interface TaskCRUD extends JpaRepository<TaskEntity, Long> {
    RoleEntity findRoleEntityByRoleName(String roleName);
    RoleEntity findRoleEntityByBoardId(Long boardId);
    RoleEntity findRoleEntityByUserId(Long userId);
}