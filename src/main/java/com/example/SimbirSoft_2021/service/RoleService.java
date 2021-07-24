package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.RoleServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class RoleService implements StandartServiceInterface, RoleServiceInterface {

    // 2 способ
    //@Autowired
    //private RoleCrud roleCRUD;

    private RoleCrud roleCRUD; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public RoleService(RoleCrud roleCRUD) {
        this.roleCRUD = roleCRUD;
    }

    @Transactional
    @Override
    public RoleEntity registration(Object o) throws RoleExistsException {
        RoleEntity roleEntity = (RoleEntity)o;
        if (roleCRUD.findByRoleNameAndBoardIdAndUserId(roleEntity.getRoleName(), roleEntity.getBoardId(), roleEntity.getUserId())!=null){
            throw new RoleExistsException();
        }
        return roleCRUD.save(roleEntity);
    }

    @Transactional
    @Override
    public List<RoleEntity> getAll() throws RoleNotFoundException {
        List<RoleEntity> list = roleCRUD.findAll();
        if (list==null){
            throw new RoleNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public RoleEntity getOne(Long id) throws RoleNotFoundException {
        RoleEntity roleEntity = roleCRUD.findByRoleId(id);
        if (roleEntity==null){
            throw new RoleNotFoundException();
        }
        return roleEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws RoleNotFoundException {
        if (roleCRUD.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        roleCRUD.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public RoleEntity updateOne(Long id, Object o) throws RoleNotFoundException, RoleExistsException {
        RoleEntity roleEntityNew = (RoleEntity)o;

        if (roleCRUD.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        RoleEntity roleEntity = roleCRUD.findByRoleId(id);

        if (roleCRUD.findByRoleNameAndBoardIdAndUserId(roleEntityNew.getRoleName(), roleEntityNew.getBoardId(), roleEntityNew.getUserId())!=null){
            throw new RoleExistsException();
        }

        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setBoardId(roleEntityNew.getBoardId());
        roleEntity.setUserId(roleEntityNew.getUserId());
        return roleCRUD.save(roleEntity);
    }
}
