package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.ProjectAndDateTimeExistsException;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.exception.TaskAndDateTimeExistsException;
import com.example.SimbirSoft_2021.exception.TaskExistsException;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
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
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskCrud taskCrud; // создаём интерфейс для взаимодействия с бд
    @MockBean
    private ReleaseCrud releaseCrud;
    @MockBean
    private ProjectCrud projectCrud;
    @MockBean
    private RoleService roleService;

    @Test
    void registrationTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findByTaskNameAndProjectIdAndReleaseId("Maintenance", 1L, 1L);          // почему?

        TaskDto taskDto_1 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
        // отправляем знаения и получаем новую переменную
        TaskDto taskDto_2 = taskService.registration(taskDto_1);

        // сверяем значения
        Assert.assertNotNull(taskDto_2);
        Assert.assertEquals(taskDto_1.getTaskName(), taskDto_2.getTaskName());
        Assert.assertEquals(taskDto_1.getTaskStatus(), taskDto_2.getTaskStatus());
        Assert.assertEquals(taskDto_1.getProjectId(), taskDto_2.getProjectId());
        Assert.assertEquals(taskDto_1.getReleaseId(), taskDto_2.getReleaseId());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(taskCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
    }

    @Test
    void registrationFalseTest() throws Exception {
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        //----------------------------------1----------------------ReleaseNotFoundException
        try {
            TaskDto taskDto_1 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            TaskDto taskDto_2 = taskService.registration(taskDto_1);
            Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------2----------------------TaskAndDateTimeExistsException
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new TaskEntity()) // что возвращаю
                .when(taskCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        try {
            TaskDto taskDto_1 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            TaskDto taskDto_2 = taskService.registration(taskDto_1);
            Assert.fail("Expected TaskAndDateTimeExistsException");
        } catch (TaskAndDateTimeExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------3----------------------ProjectAndDateTimeExistsException
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new ProjectEntity()) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        try {
            TaskDto taskDto_1 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            TaskDto taskDto_2 = taskService.registration(taskDto_1);
            Assert.fail("Expected ProjectAndDateTimeExistsException");
        } catch (ProjectAndDateTimeExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------4----------------------TaskExistsException
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new TaskEntity()) // что возвращаю
                .when(taskCrud)              // кому и
                .findByTaskNameAndProjectIdAndReleaseId("Maintenance", 1L, 1L);          // почему?

        try {
            TaskDto taskDto_1 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            TaskDto taskDto_2 = taskService.registration(taskDto_1);
            Assert.fail("Expected TaskExistsException");
        } catch (TaskExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllCustom() {
    }

    @Test
    void getOne() {
    }

    @Test
    void getAllByStatus() {
    }

    @Test
    void getCountByStatus() {
    }

    @Test
    void findByReleaseId() {
    }

    @Test
    void deleteOne() {
    }

    @Test
    void deleteTaskByProjectId() {
    }

    @Test
    void deleteReleaseInTask() {
    }

    @Test
    void updateOne() {
    }
}