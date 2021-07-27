package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.service.interfaceService.BoardServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class ProjectService implements StandartServiceInterface, ProjectServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private ProjectCrud projectCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public ProjectService(ProjectCrud projectCrud) {
        this.projectCrud = projectCrud;
    }

    @Transactional
    @Override
    public ProjectEntity registration(Object o) throws ProjectExistsException {
        ProjectEntity projectEntity = (ProjectEntity)o;
        if (projectCrud.findProjectEntityByProjectName(projectEntity.getProjectName())!=null){
            throw new ProjectExistsException();
        }
        return projectCrud.save(projectEntity);
    }

    @Transactional
    @Override
    public List<ProjectEntity> getAll() throws ProjectNotFoundException {
        List<ProjectEntity> list = projectCrud.findAll();
        if (list==null){
            throw new ProjectNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public ProjectEntity getOne(Long id) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }
        return projectEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws ProjectNotFoundException {
        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }
        projectCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public ProjectEntity updateOne(Long id, Object o) throws ProjectNotFoundException, ProjectExistsException {
        ProjectEntity projectEntityNew = (ProjectEntity)o;

        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        if (projectCrud.findProjectEntityByProjectName(projectEntity.getProjectName())!=null){
            throw new ProjectExistsException();
        }
        projectEntity.setProjectName(projectEntityNew.getProjectName());
        projectEntity.setProjectStatus(projectEntityNew.getProjectStatus());
        return projectCrud.save(projectEntity);
    }
}
