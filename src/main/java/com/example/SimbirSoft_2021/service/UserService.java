package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.UserMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.UserCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class UserService implements StandartServiceInterface, UserServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private final UserCrud userCrud; // создаём интерфейс для взаимодействия с бд
    private final RoleService roleService;

    // 3 способ
    public UserService(UserCrud userCrud, RoleService roleService) {
        this.userCrud = userCrud;
        this.roleService = roleService;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- регистрация
    public UserDto registration(Object o) throws UserExistsException {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity((UserDto) o);

        //  проверка
        if ((userCrud.findByFirstNameAndLastName(userEntity.getFirstName(), userEntity.getLastName())!=null)){ // проверить, что есть такая реализация существует
            throw new UserExistsException();
        }

        // сохраняем
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- вытащить всех людей
    public List<UserDto> getAll() throws UserNotFoundException {
        List<UserEntity> userEntityList = userCrud.findAll();

        //  проверка на то что люди вообще существуют
        if (userEntityList==null){
            throw new UserNotFoundException();
        }

        List<UserDto> userDtoList = new ArrayList<>();

        //  вытаскиваем по одному человеку и сохраняем в коллекцию
        for (UserEntity e:userEntityList){
            userDtoList.add(UserMapper.INSTANCE.toDto(e));
        }

        return userDtoList;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override  // ----------------- вытащить одного человека
    public UserDto getOne(Long id) throws UserNotFoundException {
        UserEntity userEntity = userCrud.findByUserId(id);

        //  проверка на то что человек вообще существуют
        if (userEntity==null){
            throw new UserNotFoundException();
        }

        return UserMapper.INSTANCE.toDto(userEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- удалить одного человека
    public Long deleteOne(Long id) throws UserNotFoundException, RoleNotFoundException {

        //  проверка на то что человек вообще существуют
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }

        roleService.deleteByUserId(id);
        userCrud.deleteById(id);
        return id;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- обновить одного человека
    public UserDto updateOne(Long id, Object o) throws UserNotFoundException, UserExistsException {

        //  проверка на то что человек вообще существуют
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }
        UserEntity userEntityNew = UserMapper.INSTANCE.toEntity((UserDto) o);
        UserEntity userEntity = userCrud.findByUserId(id);

        //  проверка
        if ((userCrud.findByFirstNameAndLastName(userEntityNew.getFirstName(), userEntityNew.getLastName())!=null)){ // проверить, что есть такая реализация существует
            throw new UserExistsException();
        }

        // присваивание новых значений
        userEntity.setFirstName(userEntityNew.getFirstName());
        userEntity.setLastName(userEntityNew.getLastName());
        userEntity.setPatronymic(userEntityNew.getPatronymic());

        // сохранение
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }
}
