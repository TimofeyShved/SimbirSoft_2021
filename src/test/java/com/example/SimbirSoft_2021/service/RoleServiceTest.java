package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.entity.*;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.exception.RoleExistsException;
import com.example.SimbirSoft_2021.exception.TaskNotFoundException;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleCrud roleCrud; // создаём интерфейс для взаимодействия с бд
    @MockBean
    private UserCrud userCrud;
    @MockBean
    private TaskCrud taskCrud;

    @Test
    void registrationTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new TaskEntity()) // что возвращаю
                .when(taskCrud)              // кому и
                .findByTaskId(1L);          // почему?
        Mockito.doReturn(new UserEntity()) // что возвращаю
                .when(userCrud)              // кому и
                .findByUserId(1L);          // почему?
        Mockito.doReturn(null) // что возвращаю
                .when(roleCrud)              // кому и
                .findByRoleNameAndTaskIdAndUserId("implementer", 1L, 1L);          // почему?

        RoleDto roleDto_1 = new RoleDto("implementer", 1L, 1L);
        // отправляем знаения и получаем новую переменную
        RoleDto roleDto_2 = roleService.registration(roleDto_1);

        // сверяем значения
        Assert.assertNotNull(roleDto_2);
        Assert.assertEquals(roleDto_1.getRoleName(), roleDto_2.getRoleName());
        Assert.assertEquals(roleDto_1.getTaskId(), roleDto_2.getTaskId());
        Assert.assertEquals(roleDto_1.getUserId(), roleDto_2.getUserId());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(roleCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
    }

    @Test
    void registrationFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findByTaskId(1L);          // почему?

        //----------------------------------1----------------------TaskNotFoundException
        try {
            RoleDto roleDto_1 = new RoleDto("implementer", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            RoleDto roleDto_2 = roleService.registration(roleDto_1);
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------2----------------------UserNotFoundException
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new TaskEntity()) // что возвращаю
                .when(taskCrud)              // кому и
                .findByTaskId(2L);          // почему?
        Mockito.doReturn(null) // что возвращаю
                .when(userCrud)              // кому и
                .findByUserId(1L);          // почему?

        try {
            RoleDto roleDto_1 = new RoleDto("implementer", 2L, 1L);
            // отправляем знаения и получаем новую переменную
            RoleDto roleDto_2 = roleService.registration(roleDto_1);
            Assert.fail("Expected UserNotFoundException");
        } catch (UserNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------3----------------------RoleExistsException
        Mockito.doReturn(new UserEntity()) // что возвращаю
                .when(userCrud)              // кому и
                .findByUserId(2L);          // почему?
        Mockito.doReturn(new RoleEntity()) // что возвращаю
                .when(roleCrud)              // кому и
                .findByRoleNameAndTaskIdAndUserId("implementer", 2L, 2L);          // почему?
        try {
            RoleDto roleDto_1 = new RoleDto("implementer", 2L, 2L);
            // отправляем знаения и получаем новую переменную
            RoleDto roleDto_2 = roleService.registration(roleDto_1);
            Assert.fail("Expected RoleExistsException");
        } catch (RoleExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAll() throws Exception{
    }

    @Test
    void getOne() throws Exception{
    }

    @Test
    void deleteOne() throws Exception{
    }

    @Test
    void deleteByTaskId() throws Exception{
    }

    @Test
    void deleteByUserId() throws Exception{
    }

    @Test
    void updateOne() throws Exception{
    }
}