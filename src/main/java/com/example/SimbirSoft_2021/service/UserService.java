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

    private UserCrud userCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public UserService(UserCrud userCrud) {
        this.userCrud = userCrud;
    }

    @Transactional
    @Override
    public UserDto registration(Object o) throws UserExistsException {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity((UserDto) o);
        if ((userCrud.findByFirstNameAndLastName(userEntity.getFirstName(), userEntity.getLastName())!=null)){
            throw new UserExistsException();
        }
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Transactional
    @Override
    public List<UserDto> getAll() throws UserNotFoundException {
        List<UserEntity> userEntityList = userCrud.findAll();
        if (userEntityList==null){
            throw new UserNotFoundException();
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity e:userEntityList){
            userDtoList.add(UserMapper.INSTANCE.toDto(e));
        }
        return userDtoList;
    }

    @Transactional
    @Override
    public UserDto getOne(Long id) throws UserNotFoundException {
        UserEntity userEntity = userCrud.findByUserId(id);
        if (userEntity==null){
            throw new UserNotFoundException();
        }
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws UserNotFoundException {
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }
        userCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public UserDto updateOne(Long id, Object o) throws UserNotFoundException, UserExistsException {
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }
        UserEntity userEntityNew = UserMapper.INSTANCE.toEntity((UserDto) o);
        UserEntity userEntity = userCrud.findByUserId(id);

        if ((userCrud.findByFirstNameAndLastName(userEntityNew.getFirstName(), userEntityNew.getLastName())!=null)){
            throw new UserExistsException();
        }
        userEntity.setFirstName(userEntityNew.getFirstName());
        userEntity.setLastName(userEntityNew.getLastName());
        userEntity.setPatronymic(userEntityNew.getPatronymic());
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }
}
