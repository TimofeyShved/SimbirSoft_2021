package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectCrud projectCRUD; // создаём интерфейс для взаимодействия с бд

    public ProjectEntity registration(ProjectEntity projectEntity) throws Exception {
        if (projectCRUD.findProjectEntityByProjectName(projectEntity.getProjectName())!=null){
            throw new Exception("code: PROJECT_EXISTS");
        }
        return projectCRUD.save(projectEntity);
    }

    public ProjectEntity getOne(Long id) throws UserNotFoundException {
        ProjectEntity projectEntity = projectCRUD.findById(id).get();
        if (projectEntity==null){
            throw new UserNotFoundException("code: PROJECT_NOT_FOUND");
        }
        return projectEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (projectCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: PROJECT_NOT_FOUND");
        }
        projectCRUD.deleteById(id);
        return id;
    }

    public ProjectEntity updateOne(Long id, ProjectEntity projectEntityNew) throws Exception {
        ProjectEntity projectEntity = projectCRUD.findById(id).get();
        if (projectCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: PROJECT_NOT_FOUND");
        }
        if (projectCRUD.findProjectEntityByProjectName(projectEntityNew.getProjectName())!=null){
            throw new Exception("code: PROJECT_EXISTS");
        }
        projectEntity.setProjectName(projectEntityNew.getProjectName());
        projectEntity.setProjectStatus(projectEntityNew.getProjectStatus());
        return projectCRUD.save(projectEntity);
    }
}
