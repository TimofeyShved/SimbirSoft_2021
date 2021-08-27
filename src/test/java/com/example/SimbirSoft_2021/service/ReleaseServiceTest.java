package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.ReleaseDateFormatException;
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
    }

    @Test
    void getOneTest() throws Exception{
    }

    @Test
    void deleteOneTest() throws Exception{
    }

    @Test
    void updateOneTest() throws Exception{
    }
}