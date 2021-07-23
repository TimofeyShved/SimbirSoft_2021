package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleCrud roleCRUD; // создаём интерфейс для взаимодействия с бд

    public RoleEntity registration(RoleEntity roleEntity) throws Exception {
        if ((roleCRUD.findRoleEntityByRoleName(roleEntity.getRoleName())!=null)&&(roleCRUD.findRoleEntityByUserId(roleEntity.getUserId())!=null)){
            throw new Exception("code: ROLE_EXISTS");
        }
        return roleCRUD.save(roleEntity);
    }

    public RoleEntity getOne(Long id) throws UserNotFoundException {
        RoleEntity roleEntity = roleCRUD.findById(id).get();
        if (roleEntity==null){
            throw new UserNotFoundException("code: ROLE_NOT_FOUND");
        }
        return roleEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (roleCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: ROLE_NOT_FOUND");
        }
        roleCRUD.deleteById(id);
        return id;
    }

    public RoleEntity updateOne(Long id, RoleEntity roleEntityNew) throws Exception {
        RoleEntity roleEntity = roleCRUD.findById(id).get();
        if (roleCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: ROLE_NOT_FOUND");
        }
        if ((roleCRUD.findRoleEntityByRoleName(roleEntityNew.getRoleName())!=null)&&(roleCRUD.findRoleEntityByUserId(roleEntityNew.getUserId())!=null)){
            throw new Exception("code: ROLE_EXISTS");
        }
        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setBoardId(roleEntityNew.getBoardId());
        roleEntity.setUserId(roleEntityNew.getUserId());
        return roleCRUD.save(roleEntity);
    }
}
