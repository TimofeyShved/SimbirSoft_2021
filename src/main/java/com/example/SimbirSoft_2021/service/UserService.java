package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.UserCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public UserEntity registration(Object o) throws UserExistsException {
        UserEntity userEntity = (UserEntity)o;
        if ((userCrud.findByFirstNameAndLastName(userEntity.getFirstName(), userEntity.getLastName())!=null)){
            throw new UserExistsException();
        }
        return userCrud.save(userEntity);
    }

    @Transactional
    @Override
    public List<UserEntity> getAll() throws UserNotFoundException {
        List<UserEntity> list = userCrud.findAll();
        if (list==null){
            throw new UserNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public UserEntity getOne(Long id) throws UserNotFoundException {
        UserEntity userEntity = userCrud.findByUserId(id);
        System.out.println(userEntity.getFirstName());
        if (userEntity==null){
            throw new UserNotFoundException();
        }
        return userEntity;
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
    public UserEntity updateOne(Long id, Object o) throws UserNotFoundException, UserExistsException {
        UserEntity userEntityNew = (UserEntity)o;

        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userCrud.findByUserId(id);

        if ((userCrud.findByFirstNameAndLastName(userEntityNew.getFirstName(), userEntityNew.getLastName())!=null)){
            throw new UserExistsException();
        }
        userEntity.setFirstName(userEntityNew.getFirstName());
        userEntity.setLastName(userEntityNew.getLastName());
        return userCrud.save(userEntity);
    }
}
