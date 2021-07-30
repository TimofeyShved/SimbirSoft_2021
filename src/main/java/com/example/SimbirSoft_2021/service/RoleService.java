package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.RoleMapper;
import com.example.SimbirSoft_2021.repository.*;
import com.example.SimbirSoft_2021.service.interfaceService.RoleServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class RoleService implements StandartServiceInterface, RoleServiceInterface {

    // 2 способ
    //@Autowired
    //private RoleCrud roleCRUD;

    private RoleCrud roleCrud; // создаём интерфейс для взаимодействия с бд
    private UserCrud userCrud;
    private TaskCrud taskCrud;

    // 3 способ

    public RoleService(RoleCrud roleCrud, UserCrud userCrud, TaskCrud taskCrud) {
        this.roleCrud = roleCrud;
        this.userCrud = userCrud;
        this.taskCrud = taskCrud;
    }

    @Transactional
    @Override
    public RoleDto registration(Object o) throws RoleExistsException, TaskNotFoundException, UserNotFoundException {
        RoleDto roleDto = (RoleDto) o;
        if ((taskCrud.findByTaskId(roleDto.getTaskId())==null)){
            throw new TaskNotFoundException();
        }
        if (userCrud.findByUserId(roleDto.getUserId())==null){
            throw new UserNotFoundException();
        }
        RoleEntity roleEntity = RoleMapper.INSTANCE.toEntity(roleDto);
        if (roleCrud.findByRoleNameAndTaskIdAndUserId(roleEntity.getRoleName(), roleEntity.getTaskId(), roleEntity.getUserId())!=null){
            throw new RoleExistsException();
        }
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

    @Transactional
    @Override
    public List<RoleDto> getAll() throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (RoleEntity e:roleEntityList){
            roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
        }
        return roleDtoList;
    }

    @Transactional
    @Override
    public RoleDto getOne(Long id) throws RoleNotFoundException {
        RoleEntity roleEntity = roleCrud.findByRoleId(id);
        if (roleEntity==null){
            throw new RoleNotFoundException();
        }
        return RoleMapper.INSTANCE.toDto(roleEntity);
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
    public RoleDto updateOne(Long id, Object o) throws RoleNotFoundException, RoleExistsException, TaskNotFoundException, UserNotFoundException {
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        RoleEntity roleEntityNew = RoleMapper.INSTANCE.toEntity((RoleDto) o);
        RoleEntity roleEntity = roleCrud.findByRoleId(id);

        if ((taskCrud.findByTaskId(roleEntityNew.getTaskId())==null)){
            throw new TaskNotFoundException();
        }
        if (userCrud.findByUserId(roleEntityNew.getUserId())==null){
            throw new UserNotFoundException();
        }
        if (roleCrud.findByRoleNameAndTaskIdAndUserId(roleEntityNew.getRoleName(), roleEntityNew.getTaskId(), roleEntityNew.getUserId())!=null){
            throw new RoleExistsException();
        }

        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setTaskId(roleEntityNew.getTaskId());
        roleEntity.setUserId(roleEntityNew.getUserId());
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }
}
