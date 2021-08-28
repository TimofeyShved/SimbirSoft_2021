package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.enumertion.StatusEnum;
import com.example.SimbirSoft_2021.exception.*;
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

import java.util.ArrayList;
import java.util.List;

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
    void getAllTest() throws Exception{
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(new TaskEntity("Maintenance", StatusEnum.BACKLOG, 1L, 1L));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(taskEntityList) // что возвращаю
                .when(taskCrud)              // кому и
                .findAll();          // почему?

        // отправляем знаения и получаем новую переменную
        List<TaskDto> taskDtoList = taskService.getAll();

        // сверяем значения
        Assert.assertNotNull(taskDtoList);
        Assert.assertEquals(taskDtoList.get(0).getTaskName(), "Maintenance");
        Assert.assertEquals(taskDtoList.get(0).getTaskStatus(), "BACKLOG");
        Assert.assertEquals(taskDtoList.get(0).getProjectId(), new Long(1));
        Assert.assertEquals(taskDtoList.get(0).getReleaseId(), new Long(1));
    }

    @Test
    void getAllFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findAll();          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            List<TaskDto> taskDtoList = taskService.getAll();
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAllCustomTest() throws Exception{
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(new TaskEntity("Maintenance", StatusEnum.BACKLOG, 1L, 1L));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(taskEntityList) // что возвращаю
                .when(taskCrud)              // кому и
                .findAll();          // почему?

        //----------------------------------1----------------------
        TaskDto cusomtaskDto_1 = new TaskDto("Maintenance");
        // отправляем знаения и получаем новую переменную
        List<TaskDto> taskDtoList_1 = taskService.getAllCustom(cusomtaskDto_1);

        // сверяем значения
        Assert.assertNotNull(taskDtoList_1);
        Assert.assertEquals(taskDtoList_1.get(0).getTaskName(), "Maintenance");

        //----------------------------------2----------------------
        TaskDto cusomtaskDto_2 = new TaskDto("Maintenance", "BACKLOG");
        // отправляем знаения и получаем новую переменную
        List<TaskDto> taskDtoList_2 = taskService.getAllCustom(cusomtaskDto_2);

        // сверяем значения
        Assert.assertNotNull(taskDtoList_2);
        Assert.assertEquals(taskDtoList_2.get(0).getTaskName(), "Maintenance");
        Assert.assertEquals(taskDtoList_2.get(0).getTaskStatus(), "BACKLOG");

        //----------------------------------3----------------------
        TaskDto cusomtaskDto_3 = new TaskDto("Maintenance", "BACKLOG", 1L);
        // отправляем знаения и получаем новую переменную
        List<TaskDto> taskDtoList_3 = taskService.getAllCustom(cusomtaskDto_3);

        // сверяем значения
        Assert.assertNotNull(taskDtoList_3);
        Assert.assertEquals(taskDtoList_3.get(0).getTaskName(), "Maintenance");
        Assert.assertEquals(taskDtoList_3.get(0).getTaskStatus(), "BACKLOG");
        Assert.assertEquals(taskDtoList_3.get(0).getProjectId(), new Long(1));

        //----------------------------------4----------------------
        TaskDto cusomtaskDto_4 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
        // отправляем знаения и получаем новую переменную
        List<TaskDto> taskDtoList_4 = taskService.getAllCustom(cusomtaskDto_4);

        // сверяем значения
        Assert.assertNotNull(taskDtoList_4);
        Assert.assertEquals(taskDtoList_4.get(0).getTaskName(), "Maintenance");
        Assert.assertEquals(taskDtoList_4.get(0).getTaskStatus(), "BACKLOG");
        Assert.assertEquals(taskDtoList_4.get(0).getProjectId(), new Long(1));
        Assert.assertEquals(taskDtoList_4.get(0).getReleaseId(), new Long(1));
    }

    @Test
    void getAllCustomFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(taskCrud)              // кому и
                .findAll();          // почему?

        //----------------------------------1----------------------
        try {
            TaskDto cusomtaskDto_1 = new TaskDto("Maintenance");
            // отправляем знаения и получаем новую переменную
            List<TaskDto> taskDtoList_1 = taskService.getAllCustom(cusomtaskDto_1);
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        //----------------------------------2----------------------
        try {
            TaskDto cusomtaskDto_2 = new TaskDto("Maintenance", "BACKLOG");
            // отправляем знаения и получаем новую переменную
            List<TaskDto> taskDtoList_2 = taskService.getAllCustom(cusomtaskDto_2);
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        //----------------------------------3----------------------
        try {
            TaskDto cusomtaskDto_3 = new TaskDto("Maintenance", "BACKLOG", 1L);
            // отправляем знаения и получаем новую переменную
            List<TaskDto> taskDtoList_3 = taskService.getAllCustom(cusomtaskDto_3);
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        //----------------------------------4----------------------
        try {
            TaskDto cusomtaskDto_4 = new TaskDto("Maintenance", "BACKLOG", 1L, 1L);
            // отправляем знаения и получаем новую переменную
            List<TaskDto> taskDtoList_4 = taskService.getAllCustom(cusomtaskDto_4);
            Assert.fail("Expected TaskNotFoundException");
        } catch (TaskNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
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