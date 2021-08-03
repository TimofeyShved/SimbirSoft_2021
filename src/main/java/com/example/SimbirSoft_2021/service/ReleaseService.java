package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.mappers.ReleaseMapper;
import com.example.SimbirSoft_2021.mappers.UserMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.ReleaseServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

// 1 способ
//@RequiredArgsConstructor
@Service
public class ReleaseService implements StandartServiceInterface<ReleaseDto>, ReleaseServiceInterface {

    // 2 способ
    //@Autowired
    //private ReleaseCrud releaseCRUD;

    private final ReleaseCrud releaseCrud; // создаём интерфейс для взаимодействия с бд
    private final ProjectService projectService;
    private final TaskService taskService;
    // 3 способ
    public ReleaseService(ReleaseCrud releaseCrud, ProjectService projectService, TaskService taskService) {
        this.releaseCrud = releaseCrud;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- регистрация
    public ReleaseDto registration(ReleaseDto releaseDto) throws ReleaseExistsException, ReleaseDateFormatException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы

        //  проверка на формат
        try {
            // присваивание новых значений из форматированных под стандарт данных прешедших значений
            releaseDto.setDataStart(dateFormat.format(dateFormat.parse(releaseDto.getDataStart())));
            releaseDto.setDataEnd(dateFormat.format(dateFormat.parse(releaseDto.getDataEnd())));
        }
        catch (Exception e){
            throw new ReleaseDateFormatException();
        }

        ReleaseEntity releaseEntity = ReleaseMapper.INSTANCE.toEntity(releaseDto);

        /* // ReleaseExistsException
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntity.getDataStart(), releaseEntity.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }*/

        // сохранение
        releaseCrud.save(releaseEntity);
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- вытащить все реализации
    public List<ReleaseDto> getAll() throws ReleaseNotFoundException {
        List<ReleaseEntity> releaseEntityList = releaseCrud.findAll();

        //  проверка на то что реализации вообще существуют
        if (releaseEntityList==null){
            throw new ReleaseNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<ReleaseDto> releaseDtoList = releaseEntityList.stream().map(x-> ReleaseMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

        return releaseDtoList;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- вытащить одну реализацию
    public ReleaseDto getOne(Long id) throws ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        //  проверка на то что реализации вообще существуют
        if (releaseEntity==null){
            throw new ReleaseNotFoundException();
        }

        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- удалить одну реализацию
    public Long deleteOne(Long id) throws ReleaseNotFoundException {
        if (id!=null){
            //  проверка на то что реализации вообще существуют
            if (releaseCrud.findByReleaseId(id)==null){
                throw new ReleaseNotFoundException();
            }
            if((projectService.deleteReleaseInProject(id))&&(taskService.deleteReleaseInTask(id))){
                releaseCrud.deleteById(id);
            }
        }
        return id;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    @Override // ----------------- обновить один проект
    public ReleaseDto updateOne(Long id, ReleaseDto releaseDto) throws ReleaseNotFoundException, ReleaseExistsException, ReleaseDateFormatException {

        //  проверка на то что реализации вообще существуют
        if (releaseCrud.findByReleaseId(id)==null){
            throw new ReleaseNotFoundException();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы

        //  проверка на формат
        try { // присваивание новых значений из форматированных под стандарт данных прешедших значений
            releaseDto.setDataStart(dateFormat.format(dateFormat.parse(releaseDto.getDataStart())));
            releaseDto.setDataEnd(dateFormat.format(dateFormat.parse(releaseDto.getDataEnd())));
        }
        catch (Exception e){
            throw new ReleaseDateFormatException();
        }

        ReleaseEntity releaseEntityNew = ReleaseMapper.INSTANCE.toEntity(releaseDto);
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        //  проверка
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntityNew.getDataStart(), releaseEntityNew.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }

        // присваивание новых значений
        releaseEntity.setDataStart(releaseEntityNew.getDataStart());
        releaseEntity.setDataEnd(releaseEntityNew.getDataEnd());

        // сохранение
        releaseCrud.save(releaseEntity);
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }
}
