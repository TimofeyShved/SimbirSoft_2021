package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleCrud extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleId(Long roleId);
    RoleEntity findByRoleNameAndTaskIdAndUserId(String roleName, Long taskId, Long userId);
}
