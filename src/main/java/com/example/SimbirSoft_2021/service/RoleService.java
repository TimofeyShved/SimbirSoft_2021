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
public class RoleService implements StandartServiceInterface<RoleDto>, RoleServiceInterface {

    // 2 способ
    //@Autowired
    //private RoleCrud roleCRUD;

    private final RoleCrud roleCrud; // создаём интерфейс для взаимодействия с бд
    private final UserCrud userCrud;
    private final TaskCrud taskCrud;

    // 3 способ

    public RoleService(RoleCrud roleCrud, UserCrud userCrud, TaskCrud taskCrud) {
        this.roleCrud = roleCrud;
        this.userCrud = userCrud;
        this.taskCrud = taskCrud;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- регистрация
    public RoleDto registration(RoleDto roleDto) throws RoleExistsException, TaskNotFoundException, UserNotFoundException {

        //  проверка
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

        // сохраняем
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- вытащить все роли
    public List<RoleDto> getAll() throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        List<RoleDto> roleDtoList = new ArrayList<>();

        //  вытаскиваем по одной роли и сохраняем в коллекцию
        for (RoleEntity e:roleEntityList){
            roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
        }

        return roleDtoList;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override  // ----------------- вытащить одну роль
    public RoleDto getOne(Long id) throws RoleNotFoundException {
        RoleEntity roleEntity = roleCrud.findByRoleId(id);

        //  проверка на то что роль вообще существуют
        if (roleEntity==null){
            throw new RoleNotFoundException();
        }

        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- удалить одну роль
    public Long deleteOne(Long id) throws RoleNotFoundException {

        //  проверка на то что роль вообще существуют
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }

        roleCrud.deleteById(id);
        return id;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- удалить роли связанные с задачами
    public List<RoleDto> deleteByTaskId(Long taskId) throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        List<RoleDto> roleDtoList = new ArrayList<>();

        //  вытаскиваем и удаляем по одной роли, и сохраняем в коллекцию
        for (RoleEntity e:roleEntityList){
            if (e.getTaskId() == taskId){ //  проверка
                roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
                deleteOne(e.getRoleId());
            }
        }

        return roleDtoList;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- удалить роли связанные с человеком
    public List<RoleDto> deleteByUserId(Long userId) throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        List<RoleDto> roleDtoList = new ArrayList<>();

        //  вытаскиваем и удаляем по одной роли, и сохраняем в коллекцию
        for (RoleEntity e:roleEntityList){
            if (e.getUserId() == userId){ //  проверка
                roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
                deleteOne(e.getRoleId());
            }
        }

        return roleDtoList;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- обновить одну роль
    public RoleDto updateOne(Long id, RoleDto roleDto) throws RoleNotFoundException, RoleExistsException, TaskNotFoundException, UserNotFoundException {

        //  проверка на то что роль вообще существуют
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        RoleEntity roleEntityNew = RoleMapper.INSTANCE.toEntity(roleDto);
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

        // присваивание новых значений
        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setTaskId(roleEntityNew.getTaskId());
        roleEntity.setUserId(roleEntityNew.getUserId());

        // сохранение
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

}
