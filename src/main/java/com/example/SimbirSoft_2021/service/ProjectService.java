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

// 1 способ
//@RequiredArgsConstructor
@Service
public class ProjectService implements StandartServiceInterface, ProjectServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private ProjectCrud projectCrud; // создаём интерфейс для взаимодействия с бд
    private ReleaseCrud releaseCrud;

    // 3 способ
    public ProjectService(ProjectCrud projectCrud, ReleaseCrud releaseCrud) {
        this.projectCrud = projectCrud;
        this.releaseCrud = releaseCrud;
    }

    @Transactional
    @Override
    public ProjectDto registration(Object o) throws ProjectExistsException, ReleaseNotFoundException, ProjectAndDateTimeExistsException {
        ProjectEntity projectEntity = ProjectMapper.INSTANCE.toEntity((ProjectDto) o);
        if (releaseCrud.findByReleaseId(projectEntity.getReleaseId())==null){
            throw new ReleaseNotFoundException();
        }
        if (projectCrud.findByReleaseId(projectEntity.getReleaseId())!=null){
            throw new ProjectAndDateTimeExistsException();
        }
        if (projectCrud.findByProjectName(projectEntity.getProjectName())!=null){
            throw new ProjectExistsException();
        }
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    @Transactional
    @Override
    public List<ProjectDto> getAll() throws ProjectNotFoundException {
        List<ProjectEntity> projectEntityList = projectCrud.findAll();
        if (projectEntityList==null){
            throw new ProjectNotFoundException();
        }
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (ProjectEntity e:projectEntityList){
            projectDtoList.add(ProjectMapper.INSTANCE.toDto(e));
        }
        return projectDtoList;
    }

    @Transactional
    @Override
    public ProjectDto getOne(Long id) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws ProjectNotFoundException, TaskNotFoundException {
        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }
        projectCrud.deleteById(id);
        releaseCrud.deleteById(projectCrud.findByProjectId(id).getReleaseId());
        return id;
    }

    @Transactional
    @Override
    public ProjectDto updateOne(Long id, Object o) throws ProjectNotFoundException, ProjectExistsException, ReleaseNotFoundException, ProjectAndDateTimeExistsException {
        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }
        ProjectEntity projectEntityNew = ProjectMapper.INSTANCE.toEntity((ProjectDto) o);
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        if (releaseCrud.findByReleaseId(projectEntity.getReleaseId())==null){
            throw new ReleaseNotFoundException();
        }
        if (projectCrud.findByReleaseId(projectEntity.getReleaseId())!=null){
            throw new ProjectAndDateTimeExistsException();
        }
        if (projectCrud.findByProjectName(projectEntity.getProjectName())!=null){
            throw new ProjectExistsException();
        }
        projectEntity.setProjectName(projectEntityNew.getProjectName());
        projectEntity.setProjectStatus(projectEntityNew.getProjectStatus());
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }
}
