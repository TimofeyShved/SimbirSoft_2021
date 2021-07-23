package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//public interface RoleCrud extends CrudRepository<RoleEntity, Long> { // наследуемый интерфейс для изменения данных в бд

@Repository
public interface RoleCrud extends JpaRepository<RoleEntity, Long> {
    RoleEntity findRoleEntityByRoleName(String roleName);
    RoleEntity findRoleEntityByBoardId(Long boardId);
    RoleEntity findRoleEntityByUserId(Long userId);
}