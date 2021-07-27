package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.service.interfaceService.RoleServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
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

    private RoleCrud roleCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public RoleService(RoleCrud roleCrud) {
        this.roleCrud = roleCrud;
    }

    @Transactional
    @Override
    public RoleEntity registration(Object o) throws RoleExistsException {
        RoleEntity roleEntity = (RoleEntity)o;
        if (roleCrud.findByRoleNameAndBoardIdAndUserId(roleEntity.getRoleName(), roleEntity.getBoardId(), roleEntity.getUserId())!=null){
            throw new RoleExistsException();
        }
        return roleCrud.save(roleEntity);
    }

    @Transactional
    @Override
    public List<RoleEntity> getAll() throws RoleNotFoundException {
        List<RoleEntity> list = roleCrud.findAll();
        if (list==null){
            throw new RoleNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public RoleEntity getOne(Long id) throws RoleNotFoundException {
        RoleEntity roleEntity = roleCrud.findByRoleId(id);
        if (roleEntity==null){
            throw new RoleNotFoundException();
        }
        return roleEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws RoleNotFoundException {
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        roleCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public RoleEntity updateOne(Long id, Object o) throws RoleNotFoundException, RoleExistsException {
        RoleEntity roleEntityNew = (RoleEntity)o;

        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        RoleEntity roleEntity = roleCrud.findByRoleId(id);

        if (roleCrud.findByRoleNameAndBoardIdAndUserId(roleEntityNew.getRoleName(), roleEntityNew.getBoardId(), roleEntityNew.getUserId())!=null){
            throw new RoleExistsException();
        }

        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setBoardId(roleEntityNew.getBoardId());
        roleEntity.setUserId(roleEntityNew.getUserId());
        return roleCrud.save(roleEntity);
    }
}
