package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
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
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReleaseService releaseService;

    @MockBean
    private ProjectCrud projectCrud;
    @MockBean
    private ReleaseCrud releaseCrud;
    @MockBean
    private TaskCrud taskCrud;
    @MockBean
    private TaskService taskService;

    @Test
    void registrationTest() throws Exception{
        // создаём переменную, которую отправим в основной процесс
        ProjectDto projectDto_1 = new ProjectDto();
        // и его значения
        projectDto_1.setProjectName("PROJECT_FOUR");
        projectDto_1.setProjectStatus("BACKLOG");
        projectDto_1.setReleaseId(1L);

        ProjectEntity projectEntity = ProjectMapper.INSTANCE.toEntity(projectDto_1);

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        // отправляем знаения и получаем новую переменную
        ProjectDto projectDto_2 = projectService.registration(projectDto_1);

        // сверяем значения
        Assert.assertEquals(projectDto_1.getProjectName(), projectDto_2.getProjectName());
        Assert.assertEquals(projectDto_1.getProjectStatus(), projectDto_2.getProjectStatus());
        Assert.assertEquals(projectDto_1.getReleaseId(), projectDto_2.getReleaseId());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(projectCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
    }

    @Test
    void registrationFalseTest() throws Exception{
        // создаём переменную, которую отправим в основной процесс
        ProjectDto projectDto_1 = new ProjectDto();
        // и его значения
        projectDto_1.setProjectName("PROJECT_ONE");
        projectDto_1.setProjectStatus("BACKLOG");
        ProjectDto projectDto_2 = null;

        //----------------------------------1----------------------ReleaseNotFoundException
        try {
        // отправляем знаения и получаем новую переменную
        projectDto_2 = projectService.registration(projectDto_1);
        Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------2----------------------ProjectAndDateTimeExistsException
        // создаём переменную, которую отправим в основной процесс
        // и его значения
        projectDto_1.setReleaseId(1L);

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new ProjectEntity()) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            projectDto_2 = projectService.registration(projectDto_1);
            Assert.fail("Expected ProjectAndDateTimeExistsException");
        } catch (ProjectAndDateTimeExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------3----------------------TaskAndDateTimeExistsException
        // создаём переменную, которую отправим в основной процесс
        // и его значения
        projectDto_1.setReleaseId(2L);

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(2L);          // почему?
        Mockito.doReturn(new TaskEntity()) // что возвращаю
                .when(taskCrud)              // кому и
                .findByReleaseId(2L);          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            projectDto_2 = projectService.registration(projectDto_1);
            Assert.fail("Expected TaskAndDateTimeExistsException");
        } catch (TaskAndDateTimeExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------4----------------------ProjectExistsException
        // создаём переменную, которую отправим в основной процесс
        // и его значения
        projectDto_1.setReleaseId(3L);

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(3L);          // почему?
        Mockito.doReturn(new ProjectEntity()) // что возвращаю
                .when(projectCrud)              // кому и
                .findByProjectName("PROJECT_ONE");          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            projectDto_2 = projectService.registration(projectDto_1);
            Assert.fail("Expected ProjectExistsException");
        } catch (ProjectExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAllTest() throws Exception{
        List<ProjectEntity> projectEntityList = new ArrayList<>();
        projectEntityList.add(new ProjectEntity("PROJECT_ONE", StatusEnum.DONE,1L));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(projectEntityList) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        // отправляем знаения и получаем новую переменную
        List<ProjectDto> projectDtoList = projectService.getAll();

        // сверяем значения
        Assert.assertNotNull(projectDtoList);
        Assert.assertEquals(projectDtoList.get(0).getProjectName(), "PROJECT_ONE");
        Assert.assertEquals(projectDtoList.get(0).getProjectStatus(), "DONE");
        Assert.assertEquals(projectDtoList.get(0).getReleaseId(), new Long(1));
    }

    @Test
    void getAllFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            List<ProjectDto> projectDtoList = projectService.getAll();
            Assert.fail("Expected ProjectNotFoundException");
        } catch (ProjectNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getOneTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ProjectEntity("PROJECT_ONE", StatusEnum.DONE,1L)) // что возвращаю
                .when(projectCrud)              // кому и
                .findByProjectId(1L);          // почему?

        Long projectId = 1L;
        // отправляем знаения и получаем новую переменную
        ProjectDto projectDto = projectService.getOne(projectId);

        // сверяем значения
        Assert.assertNotNull(projectDto);
        Assert.assertEquals(projectDto.getProjectName(), "PROJECT_ONE");
        Assert.assertEquals(projectDto.getProjectStatus(), "DONE");
        Assert.assertEquals(projectDto.getReleaseId(), new Long(1));
    }

    @Test
    void getOneFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findByProjectId(1L);          // почему?

        try {
            Long projectId = 1L;
            // отправляем знаения и получаем новую переменную
            ProjectDto projectDto = projectService.getOne(projectId);
            Assert.fail("Expected ProjectNotFoundException");
        } catch (ProjectNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAllByStatusTest() throws Exception{

        List<ProjectEntity> projectEntityList = new ArrayList<>();
        projectEntityList.add(new ProjectEntity("PROJECT_ONE", StatusEnum.DONE,1L));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(projectEntityList) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        String status = "DONE";
        // отправляем знаения и получаем новую переменную
        List<ProjectDto> projectDtoList = projectService.getAllByStatus(status);

        // сверяем значения
        Assert.assertNotNull(projectDtoList);
        Assert.assertEquals(projectDtoList.get(0).getProjectName(), "PROJECT_ONE");
        Assert.assertEquals(projectDtoList.get(0).getProjectStatus(), "DONE");
        Assert.assertEquals(projectDtoList.get(0).getReleaseId(), new Long(1));
    }

    @Test
    void getAllByStatusFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        try {
            String status = "DONE";
            // отправляем знаения и получаем новую переменную
            List<ProjectDto> projectDtoList = projectService.getAllByStatus(status);
            Assert.fail("Expected ProjectNotFoundException");
        } catch (ProjectNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getCountByStatusTest() throws Exception{
        List<ProjectEntity> projectEntityList = new ArrayList<>();
        projectEntityList.add(new ProjectEntity("PROJECT_ONE", StatusEnum.DONE,1L));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(projectEntityList) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        String status = "DONE";
        // отправляем знаения и получаем новую переменную
        Long countByStatus = projectService.getCountByStatus(status);

        // сверяем значения
        Assert.assertNotNull(countByStatus);
        Assert.assertEquals(countByStatus, new Long(1));
    }

    @Test
    void getCountByStatusFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findAll();          // почему?

        try {
            String status = "DONE";
            // отправляем знаения и получаем новую переменную
            Long countByStatus = projectService.getCountByStatus(status);
            Assert.fail("Expected ProjectNotFoundException");
        } catch (ProjectNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void findByReleaseIdTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new ProjectEntity("PROJECT_ONE", StatusEnum.DONE,1L)) // что возвращаю
                .when(projectCrud)              // кому и
                .findByReleaseId(2L);          // почему?

        Long releaseId = 1L;
        // отправляем знаения и получаем новую переменную
        ProjectDto projectDto = projectService.findByReleaseId(releaseId);

        // сверяем значения
        Assert.assertNull(projectDto);

        releaseId = 2L;
        // отправляем знаения и получаем новую переменную
        projectDto = projectService.findByReleaseId(releaseId);

        // сверяем значения
        Assert.assertNotNull(projectDto);
        Assert.assertEquals(projectDto.getProjectName(), "PROJECT_ONE");
        Assert.assertEquals(projectDto.getProjectStatus(), "DONE");
        Assert.assertEquals(projectDto.getReleaseId(), new Long(1));
    }

    @Test
    void deleteOneTest() throws Exception{
        Long projectId = 1l;
        projectService.deleteOne(projectId);
    }

    @Test
    void deleteReleaseInProjectTest() throws Exception{
    }

    @Test
    void updateOneTest() throws Exception{
        ProjectDto projectDto = null;
        Long projectId = 1l;
        projectService.updateOne(projectId, projectDto);
    }
}