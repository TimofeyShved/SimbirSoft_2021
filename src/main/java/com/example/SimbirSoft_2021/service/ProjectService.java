package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// 1 способ
//@RequiredArgsConstructor
@Service
public class ProjectService implements StandartServiceInterface, ProjectServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private ProjectCrud projectCrud; // создаём интерфейс для взаимодействия с бд
    private ReleaseService releaseService;
    private TaskService taskService;

    // 3 способ
    public ProjectService(ProjectCrud projectCrud, ReleaseService releaseService, TaskService taskService) {
        this.projectCrud = projectCrud;
        this.releaseService = releaseService;
        this.taskService = taskService;
    }

    @Transactional
    @Override // ----------------- регистрация
    public ProjectDto registration(Object o) throws ProjectExistsException, ReleaseNotFoundException, ProjectAndDateTimeExistsException, TaskAndDateTimeExistsException, TaskNotFoundException {
        ProjectEntity projectEntity = ProjectMapper.INSTANCE.toEntity((ProjectDto) o);

        //  проверка
        if (releaseService.getOne(projectEntity.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if (projectCrud.findByReleaseId(projectEntity.getReleaseId())!=null){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        if (taskService.findByReleaseId(projectEntity.getReleaseId())!=null){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if (projectCrud.findByProjectName(projectEntity.getProjectName())!=null){ // что имя проекта не повторяется
            throw new ProjectExistsException();
        }

        //  сохранение
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    @Transactional
    @Override // ----------------- вытащить все проекты
    public List<ProjectDto> getAll() throws ProjectNotFoundException {
        List<ProjectEntity> projectEntityList = projectCrud.findAll();

        //  проверка на то что проекты вообще существуют
        if (projectEntityList==null){
            throw new ProjectNotFoundException();
        }

        List<ProjectDto> projectDtoList = new ArrayList<>();

        //  вытаскиваем по одному проекту и сохраняем в коллекцию
        for (ProjectEntity e:projectEntityList){
            projectDtoList.add(ProjectMapper.INSTANCE.toDto(e));
        }

        return projectDtoList;
    }

    @Transactional
    @Override // ----------------- вытащить один проект
    public ProjectDto getOne(Long id) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        //  проверка на то что проект вообще существуют
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }

        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    @Transactional
    @Override // ----------------- удалить один проект
    public Long deleteOne(Long id) throws ProjectNotFoundException, TaskNotFoundException, ReleaseNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        //  проверка на то что проект вообще существуют
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }

        releaseService.deleteOne(projectEntity.getReleaseId());
        taskService.deleteByProjectId(id);
        projectCrud.delete(projectEntity);
        return id;
    }

    @Transactional
    @Override // ----------------- обновить один проект
    public ProjectDto updateOne(Long id, Object o) throws ProjectNotFoundException, ProjectExistsException, ReleaseNotFoundException,
            ProjectAndDateTimeExistsException, TaskAndDateTimeExistsException, TaskNotFoundException {

        //  проверка на то что проект вообще существуют
        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }

        // получение сущности нового проекта
        ProjectEntity projectEntityNew = ProjectMapper.INSTANCE.toEntity((ProjectDto) o);

        // получение сущности старого проекта
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        // проверка
        if (releaseService.getOne(projectEntityNew.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if ((projectCrud.findByReleaseId(projectEntityNew.getReleaseId())!=null)
                &&(projectEntity.getReleaseId()!=projectEntityNew.getReleaseId())){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        if (taskService.findByReleaseId(projectEntityNew.getReleaseId())!=null){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if ((projectCrud.findByProjectName(projectEntityNew.getProjectName())!=null)
                &&(!Objects.equals(projectEntity.getProjectName(), projectEntityNew.getProjectName()))){ // что имя проекта не повторяется
            throw new ProjectExistsException();
        }

        // присваивание новых значений
        projectEntity.setProjectName(projectEntityNew.getProjectName());
        projectEntity.setProjectStatus(projectEntityNew.getProjectStatus());
        projectEntity.setReleaseId(projectEntityNew.getReleaseId());

        // сохранение
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }
}
