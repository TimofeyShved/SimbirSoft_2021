package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    public UserEntity registration(UserEntity userEntity) throws Exception {
        System.out.println(userEntity);
        if ((userCRUD.findUserEntityByFirstName(userEntity.getFirstName())!=null)&&(userCRUD.findUserEntityBylastName(userEntity.getLastName())!=null)){
            throw new Exception("code: USER_EXISTS");
        }
        return userCRUD.save(userEntity);
    }

    public UserEntity getOne(Long id) throws UserNotFoundException {
        UserEntity user = userCRUD.findById(id).get();
        if (user==null){
            throw new UserNotFoundException("code: USER_NOT_FOUND");
        }
        return user;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (userCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: USER_NOT_FOUND");
        }
        userCRUD.deleteById(id);
        return id;
    }

    public UserEntity updateOne(Long id, UserEntity userNew) throws Exception {
        UserEntity user = userCRUD.findById(id).get();
        if (userCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: USER_NOT_FOUND");
        }
        if ((userCRUD.findUserEntityByFirstName(userNew.getFirstName())!=null)&&(userCRUD.findUserEntityBylastName(userNew.getLastName())!=null)){
            throw new Exception("code: USER_EXISTS");
        }
        user.setFirstName(userNew.getFirstName());
        user.setLastName(userNew.getLastName());
        return userCRUD.save(user);
    }
}
