package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.enumertion.StatusEnum;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.exception.TaskNotFoundException;
import com.example.SimbirSoft_2021.exception.UserExistsException;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.model.UserModel;
import com.example.SimbirSoft_2021.repository.UserCrud;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserCrud userCrud; // создаём интерфейс для взаимодействия с бд
    @MockBean
    private RoleService roleService;

    @Test
    void registrationTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(userCrud)              // кому и
                .findByEmail("aaa@mail.ru");          // почему?

        UserDto userDto_1 = new UserDto("Angelina","Jolie", "Voight", "aaa@mail.ru", "123");
        // отправляем знаения и получаем новую переменную
        UserDto userDto_2 = userService.registration(userDto_1);

        // сверяем значения
        Assert.assertNotNull(userDto_2);
        Assert.assertEquals(userDto_1.getFirstName(), userDto_2.getFirstName());
        Assert.assertEquals(userDto_1.getLastName(), userDto_2.getLastName());
        Assert.assertEquals(userDto_1.getPatronymic(), userDto_2.getPatronymic());
        Assert.assertEquals(userDto_1.getEmail(), userDto_2.getEmail());
        Assert.assertEquals(userDto_1.getPassword(), userDto_2.getPassword());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(userCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
    }

    @Test
    void registrationFalseTest() throws Exception {
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new UserEntity()) // что возвращаю
                .when(userCrud)              // кому и
                .findByEmail("aaa@mail.ru");          // почему?

        //----------------------------------1----------------------UserExistsException
        try {
            UserDto userDto_1 = new UserDto("Angelina","Jolie", "Voight", "aaa@mail.ru", "123");
            // отправляем знаения и получаем новую переменную
            UserDto userDto_2 = userService.registration(userDto_1);
            Assert.fail("Expected UserExistsException");
        } catch (UserExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAllTest() throws Exception{
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity(1L,"Angelina","Jolie", "Voight", "aaa@mail.ru", "123"));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(userEntityList) // что возвращаю
                .when(userCrud)              // кому и
                .findAll();          // почему?

        // отправляем знаения и получаем новую переменную
        List<UserModel> userModelList = userService.getAll();

        // сверяем значения
        Assert.assertNotNull(userModelList);
        Assert.assertEquals(userModelList.get(0).getFirstName(), "Angelina");
        Assert.assertEquals(userModelList.get(0).getLastName(), "Jolie");
        Assert.assertEquals(userModelList.get(0).getPatronymic(), "Voight");
        Assert.assertEquals(userModelList.get(0).getEmail(), "aaa@mail.ru");
    }

    @Test
    void getAllFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(userCrud)              // кому и
                .findAll();          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            List<UserModel> userModelList = userService.getAll();
            Assert.fail("Expected UserNotFoundException");
        } catch (UserNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getOneTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new UserEntity(1L,"Angelina","Jolie", "Voight", "aaa@mail.ru", "123"))
                .when(userCrud)              // кому и
                .findByUserId(1L);          // почему?

        Long id = 1L;
        // отправляем знаения и получаем новую переменную
        UserModel userModel = userService.getOne(id);

        // сверяем значения
        Assert.assertNotNull(userModel);
        Assert.assertEquals(userModel.getFirstName(), "Angelina");
        Assert.assertEquals(userModel.getLastName(), "Jolie");
        Assert.assertEquals(userModel.getPatronymic(), "Voight");
        Assert.assertEquals(userModel.getEmail(), "aaa@mail.ru");
    }

    @Test
    void getOneFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(userCrud)              // кому и
                .findAll();          // почему?

        try {
            Long id = 1L;
            // отправляем знаения и получаем новую переменную
            UserModel userModel = userService.getOne(id);
            Assert.fail("Expected UserNotFoundException");
        } catch (UserNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void findByEmail() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void updateOne() {
    }
}