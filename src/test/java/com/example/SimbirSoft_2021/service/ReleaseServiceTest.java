package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.enumertion.StatusEnum;
import com.example.SimbirSoft_2021.exception.ProjectNotFoundException;
import com.example.SimbirSoft_2021.exception.ReleaseDateFormatException;
import com.example.SimbirSoft_2021.exception.ReleaseExistsException;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.thread.ReleaseThread;
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
class ReleaseServiceTest {

    @Autowired
    private ReleaseService releaseService;

    @MockBean
    private ReleaseCrud releaseCrud; // создаём интерфейс для взаимодействия с бд
    @MockBean
    private ProjectService projectService;
    @MockBean
    private TaskService taskService;
    @MockBean
    private ReleaseThread releaseThread;

    @Test
    void registrationTest() throws Exception{
        // создаём переменную, которую отправим в основной процесс
        ReleaseDto releaseDto_1 = new ReleaseDto("2021-08-21 07:00:00", "2021-08-21 20:30:00");
        // отправляем знаения и получаем новую переменную
        ReleaseDto releaseDto_2 = releaseService.registration(releaseDto_1);

        // сверяем значения
        Assert.assertNotNull(releaseDto_2);
        Assert.assertEquals(releaseDto_1.getDataStart(), releaseDto_2.getDataStart());
        Assert.assertEquals(releaseDto_1.getDataEnd(), releaseDto_2.getDataEnd());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(releaseCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
        Mockito.verify(releaseThread, Mockito.times(1)).set(ArgumentMatchers.isNotNull());
    }

    @Test
    void registrationFalseTest() throws Exception{
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("2022342342342343241--22343241 07:00:00", "2032423421--2234231 20:30:00");
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.registration(releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("20223234", "20324");
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.registration(releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("dfsdafdsfstweg", "ewfdsvreverasd");
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.registration(releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("-08-22 07:00:00", "-08-22 20:30:00");
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.registration(releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getAllTest() throws Exception{
        List<ReleaseEntity> releaseEntityList = new ArrayList<>();
        releaseEntityList.add(new ReleaseEntity("2021-08-21 07:00:00", "2021-08-21 20:30:00"));

        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(releaseEntityList) // что возвращаю
                .when(releaseCrud)              // кому и
                .findAll();          // почему?

        // отправляем знаения и получаем новую переменную
        List<ReleaseDto> releaseDtoList = releaseService.getAll();

        // сверяем значения
        Assert.assertNotNull(releaseDtoList);
        Assert.assertEquals(releaseDtoList.get(0).getDataStart(), "2021-08-21 07:00:00");
        Assert.assertEquals(releaseDtoList.get(0).getDataEnd(), "2021-08-21 20:30:00");
    }

    @Test
    void getAllFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(releaseCrud)              // кому и
                .findAll();          // почему?

        try {
            // отправляем знаения и получаем новую переменную
            List<ReleaseDto> releaseDtoList = releaseService.getAll();
            Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void getOneTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity("2021-08-21 07:00:00", "2021-08-21 20:30:00")) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        Long id = 1L;
        // отправляем знаения и получаем новую переменную
        ReleaseDto releaseDto = releaseService.getOne(id);

        // сверяем значения
        Assert.assertNotNull(releaseDto);
        Assert.assertEquals(releaseDto.getDataStart(), "2021-08-21 07:00:00");
        Assert.assertEquals(releaseDto.getDataEnd(), "2021-08-21 20:30:00");
    }

    @Test
    void getOneFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        try {
            Long id = 1L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto = releaseService.getOne(id);
            Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void deleteOneTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity("2021-08-21 07:00:00", "2021-08-21 20:30:00")) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(true) // что возвращаю
                .when(projectService)              // кому и
                .deleteReleaseInProject(1L);          // почему?
        Mockito.doReturn(true) // что возвращаю
                .when(taskService)              // кому и
                .deleteReleaseInTask(1L);          // почему?

        Long id = 1L;
        // отправляем знаения и получаем новую переменную
        Long releaseId = releaseService.deleteOne(id);

        // сверяем значения
        Assert.assertNotNull(releaseId);
        Assert.assertEquals(releaseId, new Long(1));

        // проверка на то, что выполнились действия в бд
        Mockito.verify(releaseCrud, Mockito.times(1)).deleteById(ArgumentMatchers.isNotNull());
    }

    @Test
    void deleteOneFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        try {
            Long id = 1L;
            // отправляем знаения и получаем новую переменную
            Long releaseId = releaseService.deleteOne(id);
            Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }

    @Test
    void updateOneTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByDataStartAndDataEnd("2021-08-21 07:00:00", "2021-08-21 20:30:00");          // почему?

        // создаём переменную, которую отправим в основной процесс
        ReleaseDto releaseDto_1 = new ReleaseDto("2021-08-21 07:00:00", "2022-08-21 20:30:00");
        Long id = 1L;
        // отправляем знаения и получаем новую переменную
        ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);

        // сверяем значения
        Assert.assertNotNull(releaseDto_2);
        Assert.assertEquals(releaseDto_1.getDataStart(), releaseDto_2.getDataStart());
        Assert.assertEquals(releaseDto_1.getDataEnd(), releaseDto_2.getDataEnd());

        // проверка на то, что выполнились действия в бд
        Mockito.verify(releaseCrud, Mockito.times(1)).save(ArgumentMatchers.isNotNull());
    }

    @Test
    void updateOneFalseTest() throws Exception{
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(null) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(1L);          // почему?

        //----------------------------------1----------------------ReleaseNotFoundException
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("2021-08-21 07:00:00", "2022-08-21 20:30:00");
            Long id = 1L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseNotFoundException");
        } catch (ReleaseNotFoundException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------2----------------------ReleaseDateFormatException
        // подготовка ответов на внутрении запросы
        // заставить вернуть
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByReleaseId(2L);          // почему?

        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("2022342342342343241--22343241 07:00:00", "2032423421--2234231 20:30:00");
            Long id = 2L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("20223234", "20324");
            Long id = 2L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("dfsdafdsfstweg", "ewfdsvreverasd");
            Long id = 2L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("-08-22 07:00:00", "-08-22 20:30:00");
            Long id = 2L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseDateFormatException");
        } catch (ReleaseDateFormatException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }

        //----------------------------------3----------------------ReleaseExistsException
        Mockito.doReturn(new ReleaseEntity()) // что возвращаю
                .when(releaseCrud)              // кому и
                .findByDataStartAndDataEnd("2021-08-21 07:00:00", "2021-08-21 20:30:00");          // почему?

        try {
            // создаём переменную, которую отправим в основной процесс
            ReleaseDto releaseDto_1 = new ReleaseDto("2021-08-21 07:00:00", "2021-08-21 20:30:00");
            Long id = 2L;
            // отправляем знаения и получаем новую переменную
            ReleaseDto releaseDto_2 = releaseService.updateOne(id, releaseDto_1);
            Assert.fail("Expected ReleaseExistsException");
        } catch (ReleaseExistsException thrown) {
            Assert.assertNotNull("", thrown.getMessage());
        }
    }
}